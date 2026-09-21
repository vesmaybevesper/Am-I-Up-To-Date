import java.util.zip.ZipFile

plugins {
	id("mod-platform")
	id("net.neoforged.moddev")
	//id("com.github.spotbugs") version "6.5.9"
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
	replacements.string(current.parsed >= "1.20.1"){
		replace("deserializeWithResourceLocation", "deserializeWithIdentifier")
	}
	replacements.string(current.parsed >= "1.20.1"){
		replace("cefBrowser.getResourceLocation", "cefBrowser.getIdentifier")
	}
	replacements.string(current.parsed >= "1.20.1"){
		replace("browser.getResourceLocation", "browser.getIdentifier")
	}
	replacements.string(current.parsed >= "1.20.1"){
		replace("browser.getTextureResourceLocation", "browser.getTextureIdentifier")
	}
}

tasks.withType<Javadoc>().configureEach {
	(options as StandardJavadocDocletOptions).addStringOption("Xdoclint:-missing", "-quiet")
}

val supportedVersions = arrayOf("1.20.1", "1.21.1", "1.21.11", "26.1", "26.2", "26.3")

platform {
	loader = "neoforge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = "[${prop("deps.minecraft")},)"
		}
		required("neoforge") {
			forgeLikeVersionRange.set("[1,)")
		}
		required("yet_another_config_lib_v3") {
			slug("yacl")
			forgeLikeVersionRange = ">=${prop("deps.yet_another_config_lib_v3")}"
		}

		if (stonecutter.project.version in supportedVersions) {
			optional("fancymenu") {
				slug("fancymenu")
				forgeLikeVersionRange = ">=${prop("deps.fancymenu")}"
			}
			optional("rinku") {
				slug("rinku")
			forgeLikeVersionRange = ">=${prop("deps.rinku")}"
		}
		}
	}
}

/*spotbugs{
	version="4.10.3"
	ignoreFailures=true
}*/

neoForge {
	version = prop("deps.neoforge")
	accessTransformers.from(rootProject.file("src/main/resources/aw/${stonecutter.current.version}.cfg"))
	validateAccessTransformers = true

	if (hasProperty("deps.parchment")) parchment {
		val (mc, ver) = prop("deps.parchment").split(':')
		mappingsVersion = ver
		minecraftVersion = mc
	}

	runs {
		register("client") {
			client()
			gameDirectory = file("run/")
			ideName = "NeoForge Client (${stonecutter.current.version})"
			programArgument("--username=Dev")
		}
		register("server") {
			server()
			gameDirectory = file("run/")
			ideName = "NeoForge Server (${stonecutter.current.version})"
		}
	}

	mods {
		register(prop("mod.id")) {
			sourceSet(sourceSets["main"])
		}
	}
	sourceSets["main"].resources.srcDir("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
}

repositories {
	mavenCentral()
	gradlePluginPortal()
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
	maven("https://maven.isxander.dev/releases") {
		name = "Xander Maven"
	}
	maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
	maven("https://keksuccino.github.io/maven/")
}

val rinkuArtifact by configurations.creating

dependencies {
	if (stonecutter.current.version != "26.3") {
		rinkuArtifact("de.keksuccino:rinku-neoforge:${property("deps.rinku")}")
	} else {
		rinkuArtifact("maven.modrinth:bQhBuv7x:${property("deps.rinku")}")
	}
}

val rinkuClassesDir = layout.buildDirectory.dir("rinku-compile")

val unwrapRinku by tasks.registering {
	inputs.files(rinkuArtifact)
	outputs.dir(rinkuClassesDir)

	doLast {
		val dir = rinkuClassesDir.get().asFile
		dir.deleteRecursively()
		dir.mkdirs()

		val source = rinkuArtifact.singleFile
		val target = File(dir, "rinku.jar")

		ZipFile(source).use { zip ->
			val nested = zip.entries().asSequence().firstOrNull() {
				it.name.startsWith("META-INF/jarjar/") && it.name.endsWith(".jar")
			}
			if (nested != null){
				zip.getInputStream(nested).use { input ->
					target.outputStream().use { out ->
						input.copyTo(out)
					}
				}
			} else {
				source.copyTo(target, overwrite = true)
			}
		}
	}
}

dependencies {
	//spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.14.0")
	implementation("maven.modrinth:yacl:${property("deps.yet_another_config_lib_v3")}")
	compileOnly("maven.modrinth:fancymenu:${property("deps.fancymenu")}")
	if (stonecutter.current.version == "26.3") {
		compileOnly("maven.modrinth:bQhBuv7x:${property("deps.rinku")}")
	} else if (stonecutter.current.version == "1.21.1" || stonecutter.current.version == "1.21.11" || stonecutter.current.version == "26.1") {
		compileOnly(files(rinkuClassesDir.map { it.file("rinku.jar") }).builtBy(unwrapRinku))
	} else {
		compileOnly("de.keksuccino:rinku-neoforge:${property("deps.rinku")}")
	}
	implementation("com.alibaba.fastjson2:fastjson2:2.0.65")
	jarJar("com.alibaba.fastjson2:fastjson2:2.0.65")
	// implementation(libs.moulberry.mixinconstraints)
	// jarJar(libs.moulberry.mixinconstraints)
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}
