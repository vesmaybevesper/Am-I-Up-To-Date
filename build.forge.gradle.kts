plugins {
	id("mod-platform")
	id("net.neoforged.moddev.legacyforge")
	id("com.github.spotbugs") version "6.5.9"
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
}

platform {
	loader = "forge"
	dependencies {
		required("minecraft") {
			forgeLikeVersionRange = prop("deps.minecraft")
		}
		required("forge") {
			forgeLikeVersionRange.set("[1,)")
		}
	}
}

spotbugs{
	version="4.10.3"
	ignoreFailures=true
}

legacyForge {
	version = "${prop("deps.minecraft")}-${prop("deps.forge")}"

	validateAccessTransformers = true

	accessTransformers.from(
		rootProject.file("src/main/resources/aw/${sc.current.version}.cfg")
	)

	runs {
		register("client") {
			client()
			gameDirectory = file("run/")
			ideName = "Forge Client (${sc.current.version})"
			programArgument("--username=Dev")
		}
		register("server") {
			server()
			gameDirectory = file("run/")
			ideName = "Forge Server (${sc.current.version})"
		}
	}


	mods {
		register(prop("mod.id")) {
			sourceSet(sourceSets["main"])
		}
	}
}

mixin {
	add(sourceSets.main.get(), "${prop("mod.id")}.mixins.refmap.json")
	config("${prop("mod.id")}.mixins.json")
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
	annotationProcessor("org.spongepowered:mixin:${libs.versions.mixin.get()}:processor")
	spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.14.0")
	implementation("maven.modrinth:yacl:${property("deps.yacl")}")
	compileOnly("maven.modrinth:fancymenu:${property("deps.fancymenu")}")
	compileOnly("de.keksuccino:mcef-forge:${property("deps.mcef")}")
	//implementation("maven.modrinth:fastjson4yacl:${property("deps.fastjson4yacl")}")
	implementation("com.alibaba.fastjson2:fastjson2:2.0.62")
	jarJar("com.alibaba.fastjson2:fastjson2:2.0.62")

	// implementation(libs.moulberry.mixinconstraints)
	// jarJar(libs.moulberry.mixinconstraints)
}

sourceSets {
	main {
		resources.srcDir(
			"${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated"
		)
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}
