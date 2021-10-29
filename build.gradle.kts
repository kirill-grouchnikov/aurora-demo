import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0-beta5"
}

group = "org.pushing-pixels.aurora.demo"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    implementation("org.pushing-pixels:aurora-theming:0.0.59-SNAPSHOT")
    implementation("org.pushing-pixels:aurora-component:0.0.59-SNAPSHOT")
    implementation("org.pushing-pixels:aurora-window:0.0.59-SNAPSHOT")
    implementation(compose.desktop.currentOs)
    implementation("org.pushing-pixels:radiance-theming:5.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
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