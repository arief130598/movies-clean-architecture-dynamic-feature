import extensions.coreModuleDeps
import extensions.project
import extensions.testingModuleDeps

plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
}

android {
    namespace = Namespace.core
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK

        buildConfigField(BuildConfigType.string, BuildConfigName.api, BuildConfigValue.movie_api)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
}

dependencies {
    coreModuleDeps()

    testImplementation(project(Modules.core))
    testingModuleDeps()
}