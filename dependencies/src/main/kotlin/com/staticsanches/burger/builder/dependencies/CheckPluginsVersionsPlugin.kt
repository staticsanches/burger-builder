package com.staticsanches.burger.builder.dependencies

import Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript

/**
 * Checks that the configured plugins on the buildscript block is the same as the defined on [Versions].
 */
class CheckPluginsVersionsPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.buildscript {
			val kotlinPlugin = configurations.classpath.get().dependencies.find {
				it.group == "org.jetbrains.kotlin" && it.name == "kotlin-gradle-plugin"
			}
			requireNotNull(kotlinPlugin) { "Missing Kotlin gradle plugin" }
			require(kotlinPlugin.version == Versions.kotlin) { "Mismatching Kotlin versions" }

			val androidPlugin = configurations.classpath.get().dependencies.find {
				it.group == "com.android.tools.build" && it.name == "gradle"
			}
			requireNotNull(androidPlugin) { "Missing Android gradle plugin" }
			require(androidPlugin.version == Versions.androidPlugin) { "Mismatching Android versions" }
		}
	}

}
