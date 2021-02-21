package task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Task responsible for uploading the docker-compose.yml file.
 */
open class UploadDockerCompose : DefaultTask() {


	@TaskAction
	fun run() {
		// Remove the previous file
		val remoteFile = remoteHomeFolder + "docker-compose.yml"
		ssh.execute("rm $remoteFile")

		// Uploads the new one
		val localFile = folderName(project.projectDir.absolutePath) + "docker-compose.yml"
		ssh.upload(localFile, remoteFile)
	}

}
