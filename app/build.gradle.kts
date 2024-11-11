plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.dbis_elearning_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dbis_elearning_app"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        // Initialize manifestPlaceholders with mutable map directly
        manifestPlaceholders.putAll(
            mapOf(
                "auth0Domain" to "@string/com_auth0_domain",
                "auth0Scheme" to "@string/com_auth0_scheme"
            )
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation("design.spline:spline-runtime:+")
    implementation(libs.coil.compose)
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-auth")
    implementation(libs.font.awesome)
    implementation (libs.androidx.material.icons.extended)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //hilt
    implementation(libs.androidx.hilt.navigation.compose)
    //dagger-hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //video format converter ffmpeg
    implementation (libs.ffmpeg.kit.full)
    // ExoPlayer for video streaming
    implementation(libs.exoplayer)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    // Auth0 dependencies
    implementation (libs.auth0)
    implementation (libs.jwtdecode)
}