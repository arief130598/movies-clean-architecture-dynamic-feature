import extensions.implementation
import extensions.project
import extensions.room

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
    implementation(project(Modules.common))
    implementation(project(Modules.core))

    room()
}