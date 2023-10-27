plugins {
    id("com.android.dynamic-feature")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("kapt")
    kotlin("android")
}

apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.dicoding.githubapp.favorite"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core"))
    implementation(fileTree("libs") { include("*.jar") })
}