package org.track.fit

import libs
import org.gradle.api.Project
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.kmpComposeDependencies(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    val compose = ComposePlugin.Dependencies(this@kmpComposeDependencies)

    sourceSets.apply {

        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.findLibrary("androidx.lifecycle.viewmodel").get())
                implementation(libs.findLibrary("androidx.lifecycle.runtime.compose").get())
                implementation(libs.findLibrary("material3.window.size").get())
                //Navigation
                implementation(libs.findLibrary("compose.navigation").get())
                //MotionAnimation
                implementation(libs.findLibrary("material.motion.compose.core").get())
                //Koin
                implementation(libs.findLibrary("koin.compose").get())
                implementation(libs.findLibrary("koin.compose.viewmodel").get())
            }
        }

        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation(libs.findLibrary("androidx.activity.compose").get())
                // Koin
                implementation(libs.findLibrary("koin.androidx.compose").get())
            }
        }

        commonTest.dependencies {

        }
    }

//    task("testClasses")
}