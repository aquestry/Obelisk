plugins {
    kotlin("jvm") version "2.1.20"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.aquestry"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.kyori:adventure-text-minimessage:4.22.0")
    implementation("net.minestom:minestom:2025.07.17-1.21.8")
    implementation("org.slf4j:slf4j-simple:2.0.6")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("dev.aquestry.MainKt")
}

tasks.shadowJar {
    archiveClassifier.set("all")
    mergeServiceFiles()
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}