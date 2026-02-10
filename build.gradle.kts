import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "2.3.10"
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

group = "org.pushing-pixels.aurora.demo"
version = "1.0.0"

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://packages.jetbrains.team/maven/p/cmp/dev/") }
        maven { url = uri("https://central.sonatype.com/repository/maven-snapshots/") }
    }

    dependencies {
        classpath(libs.compose.desktop)
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.aurora.theming)
        classpath(libs.aurora.component)
        classpath(libs.aurora.window)
        classpath(libs.radiance.theming)
    }
}

repositories {
    google()
    mavenCentral()
    maven("https://packages.jetbrains.team/maven/p/cmp/dev/")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev")
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

configurations {
    all {
        exclude(group = "org.jetbrains.compose.material", module = "material")
        exclude(group = "org.jetbrains.compose.material3", module = "material3")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        // Force class file format for Java 11
        jvmTarget.set(JvmTarget.JVM_11)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

kotlin {
    jvm("desktop")
    sourceSets {
        named("desktopMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.desktop.common)
                api(compose.desktop.currentOs)
                api(libs.radiance.theming)
                api(libs.aurora.component)
                api(libs.aurora.theming)
                api(libs.aurora.window)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.pushingpixels.aurora.demo.HelloWorldKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "aurora-demo"
        }
    }
}