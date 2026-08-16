plugins {
    id("dev.upcraft.gradle.multiloader")
    id("net.fabricmc.fabric-loom")
}

val modID: String = providers.gradleProperty("mod_id").get()

multiLoader {
    javaVersion = libs.versions.java.map { it.toInt() }
    minecraftVersion = libs.versions.minecraft

    loader = "fabric"

    setCommonProject(":${rootProject.name}-Common")
    applyMetadataReplacements(listOf("pack.mcmeta", "*.mixins.json", "fabric.mod.json"), mapOf(
        "fabric_api_version" to libs.versions.fabric.api,
        "fabric_loader_version" to libs.versions.fabric.loader
    ))
}

repositories {
    maven("https://maven.blamejared.com") {
        name = "BlameJared"
    }
    maven("https://maven.teamresourceful.com/repository/maven-releases") {
        name = "TeamResourceful"
    }
    // FIXME currently unavailable, using backup
    // maven("https://maven.terraformersmc.com/releases") {
    maven("https://maven.gnomecraft.net/releases") {
        name = "TerraformersMC"
    }
}

dependencies {
    minecraft(libs.minecraft)

    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)

    compileOnly(libs.jei.fabric.api)
    localRuntime(libs.jei.fabric)

    compileOnly(libs.modmenu.fabric) {
        isTransitive = false
    }
    localRuntime(libs.modmenu.fabric) {
        isTransitive = false
    }

	implementation(libs.resourcefulconfig.fabric) {
        isTransitive = false
	}

    implementation(libs.sparkweave.fabric)
}

loom {
    mods {
        create(modID) {
            // Tell Loom about each source set used by your mod here. This ensures that your mod's classes are properly transformed by Loader.
            sourceSet("main")
        }
    }

    runs {
        fabricApi.configureDataGeneration {
            client = true
            // must be sparkweave so it generates for the library.
            // actual mod is set below via sparkweave.datagen.mods property
            modId = "sparkweave"
            strictValidation = true // neoforge '--all' sets '--validate' to true as well
        }

        named("datagen") {
            displayName = "Fabric Data"
            systemProperties.put("sparkweave.datagen.mods", modID)
        }
    }
}
