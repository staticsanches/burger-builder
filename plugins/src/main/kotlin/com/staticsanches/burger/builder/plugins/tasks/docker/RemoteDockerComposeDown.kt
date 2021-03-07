package com.staticsanches.burger.builder.plugins.tasks.docker

import com.staticsanches.burger.builder.plugins.tasks.remoteHomeFolder
import com.staticsanches.burger.builder.plugins.tasks.ssh
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Executes remotely "cd $remoteHomeFolder && docker-compose down".
 */
open class RemoteDockerComposeDown : DefaultTask() {

	@TaskAction
	fun run() {
		ssh.execute("cd $remoteHomeFolder && docker-compose down")
	}

}
