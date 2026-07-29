plugins {
	id("mod-platform")
	id("dev.kikugie.loom-back-compat")
	id("com.github.spotbugs") version "6.5.9"
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	// For some reason this is changing Identifier TO ResourceLocation when I change versions which is incorrect lmfao
	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
	replacements.string(current.parsed >= "26.1.2") {
		replace("FabricDataOutput", "FabricPackOutput")
	}
}

platform {
	loader = "fabric"
	dependencies {
		required("minecraft") {
			fabricLikeVersionRange = prop("deps.minecraft")
		}
		required("fabric-api") {
			slug("fabric-api")
			fabricLikeVersionRange = ">=${prop("deps.fabric-api")}"
		}
		required("fabricloader") {
			fabricLikeVersionRange = ">=${prop("deps.fabric-loader")}"
		}
		required("yacl") {
			fabricLikeVersionRange = ">=${prop("deps.yacl")}"
		}
		required("fastjson4yacl") {
			fabricLikeVersionRange = ">=${prop("deps.fastjson4yacl")}"
		}
		optional("modmenu") {}
		optional("fancymenu") {}
		optional("rinku") {}
	}
}

loom {
	accessWidenerPath = rootProject.file("src/main/resources/aw/${sc.current.version}.accesswidener")
	runs.named("client") {
		client()
		ideConfigGenerated(true)
		runDir = "run/"
		environment = "client"
		programArgs("--username=Dev")
		configName = "Fabric Client"
	}
	runs.named("server") {
		server()
		ideConfigGenerated(true)
		runDir = "run/"
		environment = "server"
		configName = "Fabric Server"
	}
}

spotbugs{
	version="4.10.3"
	ignoreFailures=true
}

fabricApi {
	configureDataGeneration {
		outputDirectory = file("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
		client = true
	}
}

repositories {
	mavenCentral()
	gradlePluginPortal()
	strictMaven("https://maven.terraformersmc.com/", "com.terraformersmc") { name = "TerraformersMC" }
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }
	maven("https://maven.isxander.dev/releases") {
		name = "Xander Maven"
	}
	maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
	maven("https://keksuccino.github.io/maven/")
}

configurations.all {
	resolutionStrategy {
		force("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
	if (sc.current.parsed < "26") {
		mappings(loom.layered {
			officialMojangMappings()
			if (hasProperty("deps.parchment"))
				parchment("org.parchmentmc.data:parchment-${prop("deps.parchment")}@zip")
		})
	}
	modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	// implementation(libs.moulberry.mixinconstraints)
	// include(libs.moulberry.mixinconstraints)
	modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric-api")}")
	modCompileOnly("com.terraformersmc:modmenu:${prop("deps.modmenu")}")
	spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.14.0")
	modImplementation("maven.modrinth:yacl:${property("deps.yacl")}")
	modCompileOnly("maven.modrinth:fancymenu:${property("deps.fancymenu")}")
	modCompileOnly("de.keksuccino:mcef-fabric:${property("deps.mcef")}")
	modImplementation("maven.modrinth:fastjson4yacl:${property("deps.fastjson4yacl")}")
	implementation("com.alibaba.fastjson2:fastjson2:2.0.62")
	include("com.alibaba.fastjson2:fastjson2:2.0.62")
}
