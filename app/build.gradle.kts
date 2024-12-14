plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.relay")
}

android {
    namespace = "com.example.dbis_elearning_app"
    compileSdk = 34
    sourceSets["main"].assets.srcDirs("src/main/ui-packages")

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
    implementation(libs.androidx.lifecycle.livedata.ktx)
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
    implementation(libs.androidx.media3.ui)
    // Auth0 dependencies
    implementation (libs.auth0)
    implementation (libs.jwtdecode)
    implementation(libs.okhttp)
    implementation (libs.auth0.v1400)
    //razar pay
    implementation (libs.checkout)
    // Room Database
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    //implementation(libs.exoplayer)
    implementation (libs.cloudinary.android)
    implementation (libs.cloudinary.core)
    implementation (libs.cloudinary.android.v231)
    val media3_version = "1.2.1"
    implementation ("androidx.media3:media3-exoplayer:$media3_version")
    implementation ("androidx.media3:media3-exoplayer-hls:$media3_version")  // For HLS support
    implementation ("androidx.media3:media3-ui:$media3_version")
    implementation ("androidx.media3:media3-common:$media3_version")
    implementation(libs.okhttp.v4120)
    implementation(libs.logging.interceptor)

    // Gson
    implementation(libs.gson)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
//    // Cloudinary SDK for Kotlin
//    implementation(libs.cloudinary.core)
//
//    // ExoPlayer for video playback
//
//    implementation (libs.cloudinary.android)
//    implementation (libs.cloudinary.android.uploader)


}