plugins {
    alias(libs.plugins.compose.feature)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "track"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            //Google Map
            implementation(libs.findLibrary("maps.compose").get())
        }

        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.common)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.services)
        }
        commonTest.dependencies {

        }
    }
}

android {

}

compose.resources{
    publicResClass = false
}