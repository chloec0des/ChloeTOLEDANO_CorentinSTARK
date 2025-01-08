plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.kapt) apply false // Correct usage of kapt plugin alias
    id("com.google.gms.google-services") version "4.4.2" apply false
}