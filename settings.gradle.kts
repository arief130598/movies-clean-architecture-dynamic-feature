pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Movies"
include(":app")
include(":core")
include(":common")
include(":domain")
include(":data")
include(":feature:home")
include(":feature:favorite")
include(":feature:search")
include(":feature:detail")
