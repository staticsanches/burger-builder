import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

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
		multiDexEnabled = true
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	lintOptions {
		tasks.lint {
			enabled = false
		}
	}

	compileOptions {
		isCoreLibraryDesugaringEnabled = true
		sourceCompatibility(JavaVersion.VERSION_1_8)
		targetCompatibility(JavaVersion.VERSION_1_8)
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
	coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}

kotlin {

	targets {
		android {
			attributes {
				attribute(androidAttribute, true)
				attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
				attribute(KotlinPlatformType.attribute, KotlinPlatformType.androidJvm)
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
			dependsOn(getByName("androidAndroidTestRelease"))

			dependencies {
				implementation(kotlin("test-junit5"))

				implementation(Dependencies.junit.jupiter.api)
				runtimeOnly(Dependencies.junit.jupiter.engine)
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
