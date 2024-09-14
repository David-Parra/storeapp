plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    //parcelable
    id("kotlin-parcelize")
    //serialization
    kotlin("plugin.serialization") version "2.0.0"
    //kapt
    id("kotlin-kapt")
    //DI HILT
    id("com.google.dagger.hilt.android")
    //GMS
    id("com.google.gms.google-services")
}

android {
    namespace = "co.edu.unab.fdam.dp.storeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "co.edu.unab.fdam.dp.storeapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    // Allow references to generated code
    kapt {
        correctErrorTypes = true
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //ConstraintLayout
    implementation(libs.androidx.constraintlayout.compose)

    //compose image load
    implementation(libs.coil.compose)

    //navigation
    implementation(libs.androidx.navigation.compose)

    //LiveData compose state
    implementation(libs.androidx.runtime.livedata)

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //DI Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //coroutine
    //flow
    //room
    val room_version = "2.6.1"

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    //Firebase authentication
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    //divider M3
    implementation("androidx.compose.material:material:1.3.0")

    //retrofit
    // Retrofit
    implementation(libs.retrofit)
    // Retrofit with Gson Converter
    implementation(libs.converter.gson)

}