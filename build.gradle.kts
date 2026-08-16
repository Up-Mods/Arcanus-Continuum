plugins {
    alias(libs.plugins.moddevgradle) apply false
    alias(libs.plugins.fabric.loom) apply false
}

val tag = providers.environmentVariable("TAG")
val buildNumber = providers.environmentVariable("BUILD_NUMBER").map { "build.${it}" }

version = tag.orElse(provider { buildString {
    append("0.1.0-development")
    if(!tag.isPresent) {
        append(buildNumber.map { "+${it}" }.getOrElse(""))
    }
} }).get()

println("Building ${project.name} $version")
