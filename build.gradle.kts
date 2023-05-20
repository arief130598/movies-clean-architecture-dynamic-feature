plugins {
    id(Plugins.ANDROID_APPLICATION) version(PluginVersion.Android) apply false
    id(Plugins.ANDROID_LIBRARY) version(PluginVersion.Android) apply false
    id(Plugins.ANDROID) version(PluginVersion.Kotlin) apply false
    id(Plugins.NAVIGATION_SAFE_ARGS) version(PluginVersion.Navigation) apply false
    id(Plugins.DAGGER_HILT) version(PluginVersion.Hilt) apply false
    id(Plugins.DOKKA) version(PluginVersion.Dokka) apply false
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}