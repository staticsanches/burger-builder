package com.staticsanches.burger.builder.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

/**
 * Applies common plugins to all projects.
 */
class CommonPluginsPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		with(target) {
			apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
			apply(plugin = "net.saliman.properties")
			apply(plugin = "dependencies")
		}
	}

}
