package org.track.fit

import com.android.build.api.dsl.CommonExtension
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }

    dependencies {
        add("implementation", libs.findLibrary("compose.bom").get())
        add("implementation", libs.findLibrary("ui.tooling.preview").get())
        add("debugImplementation", libs.findLibrary("ui.tooling").get())
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("compose_compiler_config.conf")
    }
}