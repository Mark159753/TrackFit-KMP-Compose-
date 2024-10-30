import java.util.Properties

plugins {
    alias(libs.plugins.compose.feature)
}

val apikeyPropertiesFile = rootProject.file("secrets.properties")
val apikeyProperties = Properties().apply {
    if (apikeyPropertiesFile.isFile){
        load(apikeyPropertiesFile.inputStream())
    }
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "common"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {

        }
        commonTest.dependencies {

        }
    }
}

android {
    defaultConfig {
        buildConfigField("String", "WEB_CLIENT_ID", (apikeyProperties["WEB_CLIENT_ID"] as? String) ?:  System.getenv("WEB_CLIENT_ID"))
    }

    buildFeatures.buildConfig = true
}

compose.resources{
    publicResClass = true
}
