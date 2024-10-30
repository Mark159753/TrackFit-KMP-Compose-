plugins {
    alias(libs.plugins.feature)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "local"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)

            //DataStore
            implementation(libs.findLibrary("datastore.preferences.core").get())
        }
        commonTest.dependencies {

        }
    }
}

android {
    
}
