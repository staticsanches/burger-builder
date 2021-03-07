package com.staticsanches.burger.builder.plugins.tasks.docker

import com.staticsanches.burger.builder.plugins.tasks.execute
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Executes "docker build $baseDirectory -t $dockerTag --no-cache", where dockerTag is a gradle property.
 */
open class DockerBuild : DefaultTask() {

	@Input
	lateinit var dockerTag: String

	@Input
	lateinit var baseDirectory: String

	@TaskAction
	fun run() {
		execute("docker build $baseDirectory -t $dockerTag --no-cache")
	}

}
