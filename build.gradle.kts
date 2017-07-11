import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val KOTLIN_VER = "1.1.3-2"
val VAADIN_VER = "8.0.6"

group = "org.example"
version = "0.1-SNAPSHOT"

// Section for setting up plugin repositories and dependencies.
buildscript {
    repositories {
        jcenter()
        mavenCentral()
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        // Have to manually specify the version due to a Gradle Kotlin DSL bug.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.3-2")
    }
}

plugins {
    application
    id("com.devsoap.plugin.vaadin").version("1.2.0.beta1")
}

apply {
    plugin("kotlin")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$KOTLIN_VER")
    compile("org.jetbrains.kotlin:kotlin-runtime:$KOTLIN_VER")
}

tasks {
    // Fetching the "compileKotlin" task and customising it.
    "compileKotlin"(KotlinCompile::class) {
        doLast { println("Finished compiling.") }
    }
}