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
            baseName = "domain"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.local)
        }
        commonTest.dependencies {

        }
        androidMain.dependencies {
            //Credential Manger
            implementation(libs.findLibrary("credentials").get())
            implementation(libs.findLibrary("credentials.play.services.auth").get())
        }
    }
}

android {

}
