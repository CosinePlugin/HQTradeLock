plugins {
    kotlin("jvm") version "1.8.0"
}

group = "kr.hqservice.trade.lock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.MinseoServer", "MS-Core", "1.0.19")
    compileOnly("org.bukkit:craftbukkit:1.16.5-R0.1-SNAPSHOT")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}