package com.staticsanches.burger.builder.plugins.configurations

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

fun KotlinJsTargetDsl.commonConfigurations() {
	browser {
		testTask {
			testLogging {
				showExceptions = true
				exceptionFormat = TestExceptionFormat.FULL
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
