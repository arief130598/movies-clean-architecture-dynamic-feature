import extensions.appModuleDeps
import extensions.implementation

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.DAGGER_HILT)
    id(Plugins.DOKKA)
}

android {
    namespace = AndroidConfig.APPLICATION_ID
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        dataBinding = true
    }
    dynamicFeatures += setOf(
        DynamicFeature.home,
        DynamicFeature.detail,
        DynamicFeature.search,
        DynamicFeature.favorite,
    )
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.common))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))

    appModuleDeps()
}