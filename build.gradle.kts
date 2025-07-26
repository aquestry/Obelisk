plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
}

group = "dev.aquestry"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.kyori:adventure-text-minimessage:4.23.0")
    implementation("net.minestom:minestom:2025.07.17-1.21.8")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "obelisk"
        }
    }
}