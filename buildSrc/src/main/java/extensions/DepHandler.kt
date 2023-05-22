package extensions

import Deps
import Modules
import TestDeps
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.appModuleDeps() {
    implementation(Deps.AndroidX.Navigation.ui)
    implementation(Deps.AndroidX.Navigation.fragment)
    implementation(Deps.AndroidX.Navigation.dynamicFeaturesFragment)

    api(Deps.Android.material)
    api(Deps.AndroidX.appcompat)
    api(Deps.AndroidX.Constraint.constraintLayout)

    implementation(Deps.DaggerHilt.hiltAndroid)
    kapt(Deps.DaggerHilt.hiltAndroidCompiler)
}

fun DependencyHandler.coreModuleDeps() {
    api(Deps.AndroidX.coreKtx)
    api(Deps.AndroidX.appcompat)
    api(Deps.AndroidX.legacy)
    api(Deps.AndroidX.Constraint.constraintLayout)
    api(Deps.AndroidX.Coordinator.coordinatorLayout)
    api(Deps.Shimmer.shimmer)

    api(Deps.Glide.glide)
    api(Deps.Glide.compiler)

    api(Deps.Coroutines.core)
    api(Deps.Coroutines.android)

    api(Deps.AndroidX.Lifecycle.runtime)
    api(Deps.AndroidX.Lifecycle.viewModel)
    api(Deps.AndroidX.Lifecycle.liveData)
    api(Deps.AndroidX.Lifecycle.common)

    api(Deps.AndroidX.Navigation.ui)
    api(Deps.AndroidX.Navigation.fragment)
    api(Deps.AndroidX.Navigation.commonKtx)

    implementation(Deps.DaggerHilt.hiltAndroid)
    kapt(Deps.DaggerHilt.hiltAndroidCompiler)
}

fun DependencyHandler.dataModuleDeps(){
    kapt(Deps.AndroidX.Room.compiler)
    implementation(Deps.AndroidX.Room.ktx)
    implementation(Deps.AndroidX.Room.runtime)

    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.moshiConverter)
    implementation(Deps.OkHttp.okhttp)
    implementation(Deps.OkHttp.logging)
}

fun DependencyHandler.domainModuleDeps(){
    kapt(Deps.AndroidX.Room.compiler)
    implementation(Deps.AndroidX.Room.ktx)
    implementation(Deps.AndroidX.Room.runtime)

    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.moshiConverter)
    implementation(Deps.OkHttp.okhttp)
    implementation(Deps.OkHttp.logging)

    implementation(Deps.DaggerHilt.hiltAndroid)
    kapt(Deps.DaggerHilt.hiltAndroidCompiler)
}

fun DependencyHandler.testingModuleDeps() {
    testImplementation(TestDeps.JUnit.junit)
    testImplementation(TestDeps.AndroidX.roomTest)
    testImplementation(TestDeps.AndroidX.coreTesting)
    testImplementation(TestDeps.Coroutines.coroutines)
    testImplementation(TestDeps.Mockito.core)
    testImplementation(TestDeps.Mockito.android)
    testImplementation(TestDeps.Mockito.inline)
    testImplementation(TestDeps.MockitoKotlin.mockito)
    androidTestImplementation(TestDeps.JUnit.junit)
    androidTestImplementation(TestDeps.Espresso.espressoImplementation)
}
