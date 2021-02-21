package task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.provideDelegate

/**
 * Executes "docker build . -t $dockerTag --no-cache", where dockerTag is a gradle property.
 */
open class DockerBuild : DefaultTask() {

	private val dockerTag: String by project

	@TaskAction
	fun run() {
		execute("docker build . -t $dockerTag --no-cache")
	}

}
