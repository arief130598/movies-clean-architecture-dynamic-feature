import extensions.domainModuleDeps

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

    domainModuleDeps()
}