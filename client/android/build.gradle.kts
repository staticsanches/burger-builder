import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
	id("com.android.application")
	kotlin("android")
	idea
}

android {

	compileSdkVersion(30)
	buildToolsVersion = "30.0.3"

	defaultConfig {
		applicationId = "com.staticsanches.burger.builder.android"
		minSdkVersion(16)
		targetSdkVersion(30)
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}

}

kotlin {

	target {
		attributes {
			attribute(androidAttribute, true)
			attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 6)
			attribute(KotlinPlatformType.attribute, KotlinPlatformType.androidJvm)
		}
		compilations.all {
			tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>(compileKotlinTaskName).kotlinOptions {
				jvmTarget = "1.6"
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
	implementation(project(":shared"))
	implementation(project(":client:commons"))

	implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
	implementation("androidx.core:core-ktx:1.3.2")
	implementation("androidx.appcompat:appcompat:1.2.0")
	implementation("com.google.android.material:material:1.3.0")
	implementation("androidx.constraintlayout:constraintlayout:2.0.4")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.2")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

idea {
	module {
		name = "burger-builder-client-android"
	}
}
