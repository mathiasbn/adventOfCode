import info.solidsoft.gradle.pitest.PitestPluginExtension

plugins {
    kotlin("jvm") version "1.9.0"
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "dk.hoeghbirkkjaer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotestVersion = "5.7.2"
dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation ("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-pitest:1.2.0")
}

// Assuming that you have already configured the Gradle/Maven extension
configure<PitestPluginExtension> {
    testPlugin.set("Kotest")    // needed only with old PIT <1.6.7, otherwise having kotest-extensions-pitest on classpath is enough
    junit5PluginVersion = "1.0.0"    //or 0.15 for PIT <1.9.0
    targetClasses.set(listOf("day1.*","day2.*"))
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}