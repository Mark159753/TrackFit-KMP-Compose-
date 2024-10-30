import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.track.fit.configureAndroidCompose
import org.track.fit.configureKotlinAndroid
import org.track.fit.kmpCommonDependencies
import org.track.fit.kmpComposeDependencies

class ApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.findPlugin("androidApplication").get().get().pluginId)
            apply(plugin = libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(plugin = libs.findPlugin("jetbrainsCompose").get().get().pluginId)
            apply(plugin = libs.findPlugin("compose.compiler").get().get().pluginId)
            apply(plugin = libs.findPlugin("google.services").get().get().pluginId)
            apply(plugin = libs.findPlugin("crashlytics").get().get().pluginId)
            apply(plugin = libs.findPlugin("jetbrains.kotlin.serialization").get().get().pluginId)

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk =  libs.findVersion("android.targetSdk").get().requiredVersion.toInt()
                configureAndroidCompose(this)
            }
            extensions.configure<KotlinMultiplatformExtension>{
                kmpCommonDependencies(this)
                kmpComposeDependencies(this)
            }
        }
    }

}