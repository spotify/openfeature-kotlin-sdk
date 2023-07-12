// ktlint-disable max-line-length
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint")
    kotlin("plugin.serialization") version "1.8.10"
}

android {
    namespace = "dev.openfeature.sdk"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        version = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "dev.openfeature"
                artifactId = "kotlin-sdk"
                version = "0.0.1-SNAPSHOT"

                from(components["release"])
                artifact(androidSourcesJar.get())

                pom {
                    name.set("OpenfeatureSDK")
                }
            }
        }
    }
}

val androidSourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

// Assembling should be performed before publishing package
tasks.named("publish") {
    dependsOn("assemble")
}