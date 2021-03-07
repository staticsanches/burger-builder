package com.staticsanches.burger.builder.dependencies

import Dependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Empty plugin to allow the use of [Dependencies].
 */
class DependenciesPlugin : Plugin<Project> {

	override fun apply(target: Project) {}

}
