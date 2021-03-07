package com.staticsanches.burger.builder.plugins.tasks.docker

import com.staticsanches.burger.builder.plugins.tasks.execute
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Executes "cd $baseDirectory && docker-compose down -d", where baseDirectory is the location of the docker-compose.yml file.
 */
open class DockerComposeDown : DefaultTask() {

	@Input
	lateinit var baseDirectory: String

	@TaskAction
	fun run() {
		execute("cd $baseDirectory && docker-compose down")
	}

}
