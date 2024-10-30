rootProject.name = "TrackFit"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":app")
include(":core:ui")
include(":core:local")
include(":feature:auth")
include(":core:common")
include(":core:domain")
include(":feature:registration")
include(":core:data")
include(":feature:home")
include(":feature:splash")
include(":feature:track")
include(":feature:report")
include(":feature:account")
include(":core:services")
