package com.staticsanches.burger.builder.plugins.configurations

import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun KotlinCommonOptions.commonConfigurations() {
	languageVersion = "1.4"
	apiVersion = "1.4"
	freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
}

fun KotlinSourceSet.commonConfigurations() {
	with(languageSettings) {
		progressiveMode = true
		apiVersion = "1.4"
		languageVersion = "1.4"
		useExperimentalAnnotation("kotlin.RequiresOptIn")
		useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
	}
}
