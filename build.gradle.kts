import fi.jasoft.plugin.tasks.RunTask as VaadinRun

val KOTLIN_VER = "1.1.3-2"
val VAADIN_VER = "8.0.6"

group = "org.example"
version = "0.1-SNAPSHOT"

// Section for setting up plugin repositories and dependencies.
buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        // Have to manually specify the version due to a Gradle Kotlin DSL bug.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.3-2")
        // Have to manually specify the version due to a Gradle Kotlin DSL bug.
        classpath("fi.jasoft.plugin:gradle-vaadin-plugin:1.1.12")
//        classpath("com.devsoap.plugin.vaadin:gradle-vaadin-plugin:1.2.0")
    }
}

plugins {
    application
    kotlin("jvm")
}

apply {
    //    plugin("com.devsoap.plugin.vaadin")
    plugin("fi.jasoft.plugin.vaadin")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$KOTLIN_VER")
    compile("org.jetbrains.kotlin:kotlin-runtime:$KOTLIN_VER")
}