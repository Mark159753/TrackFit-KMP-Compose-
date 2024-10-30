package org.track.fit

import libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.kmpCommonDependencies(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            // In K2 to make work @CommonParcelize add this line below (https://issuetracker.google.com/issues/315775835#comment16)
            freeCompilerArgs.addAll("-P", "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=org.track.fit.common.parcelable.CommonParcelize")
        }
    }

    //common dependencies
    sourceSets.apply {

        commonMain {
            dependencies {
                implementation(libs.findLibrary("koin.core").get())
                //Gitlive
                implementation(libs.findLibrary("gitlive.firebase.kotlin.crashlytics").get())
                implementation(libs.findLibrary("gitlive.firebase.kotlin.analytics").get())
                implementation(libs.findLibrary("gitlive.firebase.firebase.auth").get())
                implementation(libs.findLibrary("gitlive.firebase.firestore").get())
                implementation(libs.findLibrary("gitlive.firebase.database").get())

                //Kotlin Coroutines
                implementation(libs.findLibrary("kotlinx.coroutines.core").get())

                //Kotlin Immutable Collection
                implementation(libs.findLibrary("kotlinx.collections.immutable").get())

                //Logger kermit
                implementation(libs.findLibrary("logging.kermit").get())

                //DateTime
                implementation(libs.findLibrary("kotlinx.datetime").get())
            }
        }

        androidMain {
            dependencies {
                implementation(libs.findLibrary("koin.android").get() )
                //Firebase
                implementation(libs.findLibrary("firebase.bom").get() )
                implementation(libs.findLibrary("firebase.analytics").get() )
                implementation(libs.findLibrary("firebase.android.crashlytics.ktx").get() )
                implementation(libs.findLibrary("firebase.auth").get() )
                implementation(libs.findLibrary("firebase.firestore").get() )
                implementation(libs.findLibrary("firebase.database").get() )

                //Kotlin Coroutines
                implementation(libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.findLibrary("koin.test").get())
        }
    }

//    task("testClasses")
}