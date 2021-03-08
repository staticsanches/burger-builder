package com.staticsanches.burger.builder.plugins.configurations

import androidAttribute
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.attributes.java.TargetJvmVersion
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

fun KotlinAndroidTarget.commonConfigurations() {
	attributes {
		attribute(androidAttribute, true)
		attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
		attribute(KotlinPlatformType.attribute, KotlinPlatformType.androidJvm)
	}
	compilations.all {
		kotlinOptions {
			jvmTarget = "1.8"
			languageVersion = "1.4"
			apiVersion = "1.4"
			freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
		}
		project.tasks.withType<Test> {
			useJUnitPlatform()
		}
	}
}

fun BaseExtension.commonConfigurations(project: Project) {
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
		project.tasks.getByName("lint") {
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
		}
	}
}
