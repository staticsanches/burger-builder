import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import java.net.InetAddress

plugins {
	kotlin("multiplatform")
	kotlin("plugin.serialization")
	id("com.android.library")
	id("com.codingfeline.buildkonfig")
	id("net.saliman.properties")
	id("dependencies")
	idea
}

android {

	compileSdkVersion(30)
	buildToolsVersion = "30.0.3"

	defaultConfig {
		minSdkVersion(16)
		targetSdkVersion(30)
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	lintOptions {
		tasks.lint {
			enabled = false
		}
	}

	buildTypes {
		getByName("release") {
			minifyEnabled(false)
		}
	}

	sourceSets {
		getByName("main") {
			manifest.srcFile("src/androidMain/AndroidManifest.xml")
			java.srcDirs("src/androidMain/kotlin")
			res.srcDirs("src/androidMain/res")
		}
		getByName("androidTest") {
			java.srcDirs("src/androidTest/kotlin")
			res.srcDirs("src/androidTest/res")
		}
	}

}

dependencies {
	androidTestImplementation("com.android.support.test:runner:1.0.2")
}

kotlin {

	targets {
		jvm {
			attributes {
				attribute(androidAttribute, false)
				attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
				attribute(KotlinPlatformType.attribute, KotlinPlatformType.jvm)
			}
			compilations.all {
				kotlinOptions {
					jvmTarget = "1.8"
				}
				tasks.withType<Test> {
					useJUnitPlatform()
				}
			}
		}
		android {
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
				kotlinOptions {
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
				implementation(Dependencies.kotlin.x.serializationJson)
				implementation(Dependencies.kotlin.x.collectionsImmutable)
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
			dependsOn(getByName("androidAndroidTestRelease"))

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
		buildConfigField(
			com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
			"apiBaseUrl",
			apiBaseUrl
		)
	}
}

idea {
	module {
		name = "burger-builder-shared"
	}
}
