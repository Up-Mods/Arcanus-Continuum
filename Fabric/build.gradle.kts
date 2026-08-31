plugins {
    id("dev.upcraft.gradle.multiloader")
    id("dev.upcraft.gradle.custom")
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

dependencies {
    minecraft(libs.minecraft)

    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)

    compileOnly(libs.jei.fabric.api)
    localRuntime(libs.jei.fabric)

	implementation(libs.resourcefulconfig.fabric) {
        isTransitive = false
	}

    implementation(libs.sparkweave.fabric)

	implementation(libs.bundles.cca)

	implementation(libs.common.network.fabric)

	implementation(libs.trinkets.fabric)

	implementation(libs.datasync.fabric)
	include(libs.datasync.fabric)

	compileOnly(libs.modmenu.fabric) {
		isTransitive = false
	}
	localRuntime(libs.modmenu.fabric) {
		isTransitive = false
	}

	compileOnly(libs.lambdynamiclights.api.fabric)
    localRuntime(libs.lambdynamiclights.runtime.fabric)

	compileOnly(libs.sodium.fabric)
	localRuntime(libs.sodium.fabric)

	compileOnly(libs.iris.fabric)
	localRuntime(libs.iris.fabric)

	compileOnly(libs.firstperson.fabric)
//    localRuntime(libs.firstperson.fabric)

	compileOnly(libs.explosive.enhancement.fabric)
    localRuntime(libs.explosive.enhancement.fabric)

	localRuntime(libs.yeetus.experimentus.fabric)
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
