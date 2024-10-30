import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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
            baseName = "ui"
            isStatic = true
        }
    }

    configure(targets) {
        if (this is KotlinNativeTarget && konanTarget.family.isAppleFamily) {
            compilations.getByName("main") {
                val objc by cinterops.creating {
                    defFile(project.file("src/iosMain/def/objc.def"))
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.local)
            implementation(projects.core.domain)
        }
        commonTest.dependencies {

        }
        androidMain.dependencies {
            implementation(libs.findLibrary("accompanist.permissions").get())
        }
    }
}

android {
    
}

compose.resources{
    publicResClass = true
}
