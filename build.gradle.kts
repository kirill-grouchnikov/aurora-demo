import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

group = "org.pushing-pixels.aurora.demo"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation("org.pushing-pixels:aurora-theming:1.1.0")
    implementation("org.pushing-pixels:aurora-component:1.1.0")
    implementation("org.pushing-pixels:aurora-window:1.1.0")
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