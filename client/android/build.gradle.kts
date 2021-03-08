import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
	id("com.android.application")
	kotlin("android")
	id("dependencies")
	idea
}

android {

	compileSdkVersion(30)
	buildToolsVersion = "30.0.3"

	defaultConfig {
		applicationId = "com.staticsanches.burger.builder.android"
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

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}

}

kotlin {

	target {
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
			kotlinOptions {
				languageVersion = "1.4"
				apiVersion = "1.4"
				freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
			}
		}
	}

	android.sourceSets.all {
		java.srcDir("src/$name/kotlin")
	}

}

dependencies {
	implementation(kotlin("stdlib"))

	implementation(project(":shared"))
	implementation(project(":client:commons"))

	implementation(Dependencies.kotlin.x.serializationJson)

	implementation("androidx.core:core-ktx:1.3.2")
	implementation("androidx.appcompat:appcompat:1.2.0")
	implementation("com.google.android.material:material:1.3.0")
	implementation("androidx.constraintlayout:constraintlayout:2.0.4")

	coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}

idea {
	module {
		name = "burger-builder-client-android"
	}
}
