import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.track.fit.configureAndroidCompose
import org.track.fit.configureKotlinAndroid
import org.track.fit.kmpCommonDependencies
import org.track.fit.kmpComposeDependencies

class ComposeFeaturePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("androidLibrary").get().get().pluginId)
            apply(plugin = libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(plugin = libs.findPlugin("jetbrainsCompose").get().get().pluginId)
            apply(plugin = libs.findPlugin("compose.compiler").get().get().pluginId)
            apply(plugin = libs.findPlugin("kotlin.parcelize").get().get().pluginId)
            apply(plugin = libs.findPlugin("jetbrains.kotlin.serialization").get().get().pluginId)

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
            }
            extensions.configure<KotlinMultiplatformExtension>{
                kmpCommonDependencies(this)
                kmpComposeDependencies(this)
            }
        }
    }
}