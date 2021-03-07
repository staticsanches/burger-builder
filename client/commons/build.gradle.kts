import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
	kotlin("multiplatform")
	kotlin("plugin.serialization")
	id("com.codingfeline.buildkonfig")
	id("net.saliman.properties")
	id("dependencies")

	idea
}

kotlin {

	targets {
		jvm("android") {
			attributes {
				attribute(androidAttribute, true)
				attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 6)
				attribute(KotlinPlatformType.attribute, KotlinPlatformType.androidJvm)
			}
			compilations.all {
				kotlinOptions {
					jvmTarget = "1.6"
				}
				tasks.withType<Test> {
					useJUnitPlatform()
				}
			}
		}
		js {
			browser {
				testTask {
					testLogging {
						showExceptions = true
						exceptionFormat =
							org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
						showCauses = true
						showStackTraces = true
					}
				}
			}
			compilations.all {
				tasks.getByName<KotlinJsCompile>(compileKotlinTaskName).kotlinOptions {
					sourceMap = true
					sourceMapEmbedSources = "always"
					suppressWarnings = false
					verbose = true
					metaInfo = true
					main = "call"
				}
			}
		}
	}

	targets.forEach { target ->
		target.compilations.all {
			kotlinOptions {
				languageVersion = "1.4"
				apiVersion = "1.4"
				freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
			}
		}
	}

	sourceSets {

		val commonMain by getting {
			dependencies {
				implementation(project(":shared"))
			}
		}

		val commonTest by getting {
			dependencies {
				implementation(kotlin("test"))
				implementation(kotlin("test-annotations-common"))
			}
		}

		getByName("androidMain") {
			dependsOn(commonMain)
		}

		getByName("androidTest") {
			dependsOn(commonTest)

			dependencies {
				implementation(kotlin("test-junit5"))

				implementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
				runtimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit}")
			}
		}

		getByName("jsMain") {
			dependsOn(commonMain)
		}

		getByName("jsTest") {
			dependsOn(commonTest)

			dependencies {
				implementation(kotlin("test-js"))
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

buildkonfig {
	packageName = "com.staticsanches.burger.builder.client.commons"

	val firebaseUrl: String by project
	defaultConfigs {
		buildConfigField(
			com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
			"firebaseUrl",
			firebaseUrl
		)
	}
}

idea {
	module {
		name = "burger-builder-client-commons"
	}
}
