plugins {
    alias(libs.plugins.application.compose)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export(projects.feature.track)
            export(projects.core.data)
            export(projects.core.common)
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            //Google Map
            implementation(libs.findLibrary("maps.compose").get())
        }
        commonMain.dependencies {
            api(projects.core.common)
            implementation(projects.core.ui)
            implementation(projects.core.local)
            implementation(projects.core.domain)
            api(projects.core.data)
            implementation(projects.core.services)
            implementation(projects.feature.auth)
            implementation(projects.feature.registration)
            implementation(projects.feature.home)
            implementation(projects.feature.splash)
            api(projects.feature.track)
            implementation(projects.feature.report)
            implementation(projects.feature.account)
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.track.fit"
        versionCode = 1
        versionName = "1.0"

        resourceConfigurations.plus(listOf("en", "uk"))
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

