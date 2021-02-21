package task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Executes remotely "cd $remoteHomeFolder && docker-compose up -d".
 */
open class RemoteDockerComposeUp : DefaultTask() {

	@TaskAction
	fun run() {
		ssh.execute("cd $remoteHomeFolder && docker-compose up -d")
	}

}
