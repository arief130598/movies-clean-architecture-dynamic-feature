import extensions.appModuleDeps
import extensions.implementation

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.ANDROID)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.DAGGER_HILT)
    id(Plugins.DOKKA)
    kotlin(Plugins.KAPT)
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
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
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
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.common))
    implementation(project(Modules.domain))

    implementation(project(Feature.home))
    implementation(project(Feature.favorite))
    implementation(project(Feature.search))
    implementation(project(Feature.detail))

    appModuleDeps()
}