plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.myapplication"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.storage)
    implementation(libs.firebase.auth.ktx)
    kapt(libs.androidx.room.compiler)

    implementation("androidx.compose.ui:ui:1.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")
    implementation("androidx.compose.foundation:foundation:1.5.2")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.room:room-runtime:2.5.2")
    implementation(libs.androidx.room.ktx)
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.android.gms:play-services-auth:20.5.0")
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")

    debugImplementation("androidx.compose.ui:ui-tooling:1.5.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.2")

    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
}
