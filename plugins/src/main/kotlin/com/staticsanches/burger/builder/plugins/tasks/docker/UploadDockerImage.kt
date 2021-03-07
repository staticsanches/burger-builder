package com.staticsanches.burger.builder.plugins.tasks.docker

import com.staticsanches.burger.builder.plugins.tasks.execute
import com.staticsanches.burger.builder.plugins.tasks.remoteHomeFolder
import com.staticsanches.burger.builder.plugins.tasks.ssh
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import java.util.UUID

/**
 * Task responsible for saving a docker image in .tar file and uploading it to a remote DigitalOcean droplet.
 */
open class UploadDockerImage : DefaultTask() {

	@Input
	lateinit var dockerTag: String

	@TaskAction
	fun run() {
		// Save the image on a temporary .tar file
		val tarFile = Files.createTempFile("save", ".tar").toFile()
		tarFile.deleteOnExit()
		execute("docker save -o ${tarFile.absolutePath} $dockerTag")

		// Remove the previous image
		ssh.execute("docker image rm $dockerTag")

		// Upload the .tar file
		var remotePath = remoteHomeFolder + UUID.randomUUID() + ".tar"
		while (ssh.executeWithReturn("test -e $remotePath && echo true || echo false")
				.toBoolean()
		) {
			remotePath = remoteHomeFolder + UUID.randomUUID() + ".tar"
		}
		ssh.upload(tarFile.absolutePath, remotePath)

		// Load the saved image
		ssh.execute("docker load -i $remotePath")

		// Remove the .tar file
		ssh.execute("rm $remotePath")
	}

}
