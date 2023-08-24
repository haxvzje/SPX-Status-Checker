import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "baziki.spx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
sourceSets {
    main {
        java.srcDirs("src/main")
        resources.srcDirs("src/resources")
    }
}

dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.json:json:20210307")
    implementation("com.google.code.gson:gson:2.2.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("baziki.spx.MainKt")
}
