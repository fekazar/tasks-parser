plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "edu.parsing"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.16.2")
    implementation("org.apache.commons:commons-csv:1.10.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("edu.parsing.MainKt")
}
