import extensions.implementation
import extensions.kapt
import extensions.project

plugins {
    id(Plugins.ANDROID_DYNAMIC_FEATURE)
    id(Plugins.ANDROID)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.DAGGER_HILT)
    id(Plugins.DOKKA)
    kotlin(Plugins.KAPT)
}

android {
    namespace = Namespace.home
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        dataBinding = true
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
    kapt {
        correctErrorTypes = true
    }
}

kapt {
    arguments {
        // Make Hilt share the same definition of Components in tests instead of
        // creating a new set of Components per test class.
        arg("dagger.hilt.shareTestComponents", "true")
    }
}

dependencies {
    // Libraries
    implementation(project(Modules.common))
    implementation(project(Modules.core))
    implementation(project(Modules.app))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))

    // Hilt
    implementation(Deps.DaggerHilt.hiltAndroid)
    kapt(Deps.DaggerHilt.hiltAndroidCompiler)
}