plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("com.google.devtools.ksp")
//    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.pantrypal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pantrypal"
        minSdk = 22
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
    implementation(libs.androidx.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // Splash Screen API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.9")


    // Retrofit & Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room
//    val room_version = "2.6.1"
//    implementation("androidx.room:room-runtime:$room_version")
//    implementation("androidx.room:room-ktx:$room_version")
//    ksp("androidx.room:room-compiler:$room_version")
//
//// Import the Firebase BoM
//    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
//
//    // When using the BoM, you don't specify versions in Firebase library dependencies
//
//    // Add the dependency for the Firebase SDK for Google Analytics
//    implementation("com.google.firebase:firebase-analytics")
//
//    // TODO: Add the dependencies for any other Firebase products you want to use
//    // See https://firebase.google.com/docs/android/setup#available-libraries
//    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
//    implementation("com.google.firebase:firebase-auth")
//    implementation("com.google.firebase:firebase-firestore")
//
//    // (Optional) Coil for image loading
//    implementation("io.coil-kt:coil-compose:2.2.2")
}