import extensions.implementation
import extensions.kapt
import extensions.project
import extensions.testingModuleDeps

plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
}

android {
    namespace = Namespace.common
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
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.domain))

    // Hilt
    implementation(Deps.DaggerHilt.hiltAndroid)
    kapt(Deps.DaggerHilt.hiltAndroidCompiler)

    testImplementation(project(Modules.core))
    testingModuleDeps()
}