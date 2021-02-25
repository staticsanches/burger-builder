import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.InetAddress

plugins {
	kotlin("multiplatform")
	kotlin("plugin.serialization") version Versions.kotlin
	id("com.codingfeline.buildkonfig")
	id("net.saliman.properties") version Versions.salimanGradleProperties
	idea
}

kotlin {

	targets {
		jvm {
			attributes {
				attribute(androidAttribute, false)
				attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
			}
			compilations.all {
				tasks.named<KotlinCompile>(compileKotlinTaskName) {
					kotlinOptions {
						jvmTarget = "1.8"
					}
				}
				tasks.withType<Test> {
					useJUnitPlatform()
				}
			}
		}
		jvm("android") {
			attributes {
				attribute(androidAttribute, true)
				attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 6)
				attribute(KotlinPlatformType.attribute, KotlinPlatformType.androidJvm)
			}
			compilations.all {
				tasks.getByName<KotlinCompile>(compileKotlinTaskName).kotlinOptions {
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
						exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
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
				implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}")
			}
		}

		val commonTest by getting {
			dependencies {
				implementation(kotlin("test"))
				implementation(kotlin("test-annotations-common"))
			}
		}

		val javaShared by creating {
			dependsOn(commonMain)
		}

		getByName("jvmMain") {
			dependsOn(javaShared)
		}

		getByName("jvmTest") {
			dependsOn(commonTest)

			dependencies {
				implementation(kotlin("test-junit5"))

				implementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
				runtimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit}")
			}
		}

		getByName("androidMain") {
			dependsOn(javaShared)
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
	packageName = "com.staticsanches.burger.builder.shared"

	val apiPort: String by project
	val useRemoteIP: String by project
	val apiIP: String = if (useRemoteIP.toBoolean()) {
		val remoteIP: String by project
		remoteIP
	} else {
		InetAddress.getLocalHost().hostAddress
	}

	val apiBaseUrl = "http://$apiIP:$apiPort"
	defaultConfigs {
		buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "apiBaseUrl", apiBaseUrl)
	}
}

idea {
	module {
		name = "burger-builder-shared"
	}
}
