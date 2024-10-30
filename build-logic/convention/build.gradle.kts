import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "org.track.fit.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.jetbrainsCompose)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "org.track.fit.application.compose"
            implementationClass = "ApplicationComposeConventionPlugin"
        }
        register("composeFeature") {
            id = "org.track.fit.compose.feature"
            implementationClass = "ComposeFeaturePlugin"
        }
        register("feature") {
            id = "org.track.fit.feature"
            implementationClass = "FeaturePlugin"
        }
    }
}