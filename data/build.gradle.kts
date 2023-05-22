import extensions.dataModuleDeps
import extensions.implementation
import extensions.kapt
import extensions.project

plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
}

android {
    namespace = Namespace.data
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.domain))

    dataModuleDeps()

    // Hilt
    implementation(Deps.DaggerHilt.hiltAndroid)
    kapt(Deps.DaggerHilt.hiltAndroidCompiler)
}