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
            baseName = "services"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.local)
            implementation(projects.core.domain)
        }
        commonTest.dependencies {

        }
        androidMain.dependencies {
            implementation(libs.findLibrary("play.services.location").get())
        }
    }
}

android {

}
