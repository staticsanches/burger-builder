plugins {
	kotlin("js")
	kotlin("plugin.serialization") version Versions.kotlin
	id("net.saliman.properties") version Versions.salimanGradleProperties
	idea
}

dependencies {
	implementation(project(":client:commons"))

	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}")

	implementation("org.jetbrains:kotlin-react:${Versions.kotlinReact}")
	implementation("org.jetbrains:kotlin-react-dom:${Versions.kotlinReact}")
	implementation("org.jetbrains:kotlin-react-router-dom:${Versions.kotlinReactRouterDom}")
	implementation("org.jetbrains:kotlin-styled:${Versions.kotlinStyled}")

	implementation(npm("axios", "0.21.1"))
}

kotlin {

	js {
		browser {
			testTask {
				testLogging {
					showExceptions = true
					exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
					showCauses = true
					showStackTraces = true
				}
			}
		}
		compilations.all {
			tasks.getByName<org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile>(compileKotlinTaskName).kotlinOptions {
				sourceMap = true
				sourceMapEmbedSources = "always"
				suppressWarnings = false
				verbose = true
				metaInfo = true
				main = "call"
			}
			kotlinOptions {
				languageVersion = "1.4"
				apiVersion = "1.4"
				freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
			}
		}
	}

	sourceSets.all {
		languageSettings.apply {
			progressiveMode = true
			apiVersion = "1.4"
			languageVersion = "1.4"
			enableLanguageFeature("InlineClasses")
			useExperimentalAnnotation("kotlin.RequiresOptIn")
			useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
		}
	}

}

idea {
	module {
		name = "burger-builder-client-react"
	}
}
