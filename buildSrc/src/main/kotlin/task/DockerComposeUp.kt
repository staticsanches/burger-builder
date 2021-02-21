package task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Executes "cd $baseDirectory && docker-compose up -d", where baseDirectory is the location of the docker-compose.yml file.
 */
open class DockerComposeUp : DefaultTask() {

	@Input
	lateinit var baseDirectory: String

	@TaskAction
	fun run() {
		execute("cd $baseDirectory && docker-compose up -d")
	}

}
