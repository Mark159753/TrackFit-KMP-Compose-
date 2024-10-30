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
            baseName = "data"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
        }
        commonTest.dependencies {

        }
        androidMain.dependencies {
            //Credential Manger
            implementation(libs.findLibrary("credentials").get())
            implementation(libs.findLibrary("credentials.play.services.auth").get())
            implementation(libs.findLibrary("googleid").get())
        }
    }
}

android {

}
