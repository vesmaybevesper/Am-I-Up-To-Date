plugins {
	id("mod-platform")
	id("net.neoforged.moddev")
	id("com.github.spotbugs") version "6.5.9"
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
}

platform {
	loader = "neoforge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = ">=${prop("deps.minecraft")}"
		}
		required("neoforge") {
			forgeLikeVersionRange.set("[1,)")
		}
		required("yet_another_config_lib_v3") {
			slug("yacl")
			forgeLikeVersionRange = ">=${prop("deps.yet_another_config_lib_v3")}"
		}
		required("fastjson4yacl") {
			slug("fastjson4yacl")
			forgeLikeVersionRange = ">=${prop("deps.fastjson4yacl")}"
		}

		if (stonecutter.project.version == ("1.20.1") || stonecutter.project.version == ("1.21.1") || stonecutter.project.version == ("1.21.11") || stonecutter.project.version == ("26.1") || stonecutter.project.version == ("26.2")) {
			optional("fancymenu") {
				slug("fancymenu")
				forgeLikeVersionRange = ">=${prop("deps.fancymenu")}"
			}
			/*optional("rinku") {
			forgeLikeVersionRange = ">=${prop("deps.rinku")}"
		}*/
		}
	}
}

spotbugs{
	version="4.10.3"
	ignoreFailures=true
}

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

dependencies {
	spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.14.0")
	implementation("maven.modrinth:yacl:${property("deps.yet_another_config_lib_v3")}")
	compileOnly("maven.modrinth:fancymenu:${property("deps.fancymenu")}")
	compileOnly("de.keksuccino:rinku-neoforge:${property("deps.rinku")}")
	implementation("maven.modrinth:fastjson4yacl:${property("deps.fastjson4yacl")}")
	implementation("com.alibaba.fastjson2:fastjson2:2.0.63")
	jarJar("com.alibaba.fastjson2:fastjson2:2.0.63")
	// implementation(libs.moulberry.mixinconstraints)
	// jarJar(libs.moulberry.mixinconstraints)
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}
