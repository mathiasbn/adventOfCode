plugins {
    kotlin("jvm") version "1.9.0"
}

group = "dk.hoeghbirkkjaer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation ("io.kotest:kotest-assertions-core:5.7.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}