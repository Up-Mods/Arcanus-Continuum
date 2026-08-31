plugins {
    id("dev.upcraft.gradle.multiloader")
    id("dev.upcraft.gradle.custom")
    id("net.fabricmc.fabric-loom-companion")
    id("net.neoforged.moddev")
}

neoForge.neoFormVersion = libs.versions.neoform.get()

val modID = providers.gradleProperty("mod_id").get()

multiLoader {
    javaVersion = libs.versions.java.map { it.toInt() }
    minecraftVersion = libs.versions.minecraft

    applyMetadataReplacements(listOf("pack.mcmeta", "*.mixins.json"))
}

dependencies {
    compileOnly(libs.jei.api)
    compileOnly(libs.resourcefulconfig)

    compileOnly(libs.sparkweave)
    "accessTransformers"(libs.sparkweave)
    "interfaceInjectionData"(libs.sparkweave)

    compileOnly(libs.bundles.cca) // TODO ATs/interfaces

    compileOnly(libs.common.network)

    // TODO datasync common
    compileOnly(libs.datasync.neoforge)
//    accessTransformers(libs.datasync.neoforge)
    interfaceInjectionData(libs.datasync.neoforge)

    // TODO dynamic lights?

    compileOnly(libs.sodium.neoforge)

    compileOnly(libs.iris.neoforge)

    compileOnly(libs.firstperson.neoforge)

    // TODO explosive enhancement
}

neoForge {
    mods {
        register(modID) {
            sourceSet(sourceSets["main"])
        }
    }
}
