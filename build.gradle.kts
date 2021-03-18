plugins {
    java
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
}

group = "de.polylymer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
    mavenLocal()
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    //KSPIGOT
    implementation("net.axay", "KSpigot", "v1.16.5_R23")
    //SPIGOT
    compileOnly("org.spigotmc", "spigot-api", "1.16.4-R0.1-SNAPSHOT")
    //BLUEUTILS
    compileOnly("net.axay", "BlueUtils", "1.0.2")
    // KMONGO and MONGODB
    compileOnly("org.litote.kmongo", "kmongo-core", "4.2.3")
    compileOnly("org.litote.kmongo", "kmongo-serialization-mapping", "4.2.3")
}
