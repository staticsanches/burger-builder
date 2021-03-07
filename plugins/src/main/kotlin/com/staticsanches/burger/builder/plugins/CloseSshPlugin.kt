package com.staticsanches.burger.builder.plugins

import com.staticsanches.burger.builder.plugins.tasks.closeSsh
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Ensures that the ssh connection is closed when the build is finished.
 */
class CloseSshPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.gradle.buildFinished {
			closeSsh()
		}
	}

}
