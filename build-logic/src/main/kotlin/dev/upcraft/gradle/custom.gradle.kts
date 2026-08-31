package dev.upcraft.gradle

import org.gradle.kotlin.dsl.`java-library`
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    maven("https://maven.blamejared.com")
    maven("https://maven.teamresourceful.com/repository/maven-releases")
    // FIXME currently unavailable, using backup
    // maven("https://maven.terraformersmc.com/releases") {
    maven("https://maven.gnomecraft.net/releases") {
        name = "TerraformersMC"
    }
    maven("https://maven.ladysnake.org/releases")
    maven("https://maven.gegy.dev")
    maven("https://maven.nucleoid.xyz/releases")
    maven("https://maven.caffeinemc.net/releases")
}
