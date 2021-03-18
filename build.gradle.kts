plugins {
    java
    kotlin("jvm") version "1.4.31"
}

group = "de.polylymer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.axay", "KSpigot", "v1.16.5_R23")
    compileOnly("org.spigotmc", "spigot-api", "1.16.5-R0.1-SNAPSHOT")
    compileOnly("net.axay", "BlueUtils", "1.0.2")
}
