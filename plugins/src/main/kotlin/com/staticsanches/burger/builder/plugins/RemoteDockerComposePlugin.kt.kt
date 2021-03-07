package com.staticsanches.burger.builder.plugins

import com.staticsanches.burger.builder.plugins.tasks.docker.RemoteDockerComposeDown
import com.staticsanches.burger.builder.plugins.tasks.docker.RemoteDockerComposeUp
import com.staticsanches.burger.builder.plugins.tasks.docker.UploadDockerCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

class RemoteDockerComposePlugin : Plugin<Project> {

	override fun apply(target: Project) {
		val extension = target.extensions
			.create("remoteDockerCompose", RemoteDockerComposeExtension::class.java)

		target.afterEvaluate {
			val dependentModules =
				extension.dependentModules.map { "$it:uploadDockerImage" }.toTypedArray()

			tasks {

				val remoteDown by registering(RemoteDockerComposeDown::class) {
					mustRunAfter(*dependentModules)

					group = pluginGroup
					description =
						"Connects to the remote droplet and executes 'cd \$remoteHomeFolder && docker-compose down'"
				}

				register<RemoteDockerComposeUp>("remoteUp") {
					group = pluginGroup
					description =
						"Connects to the remote droplet and executes 'cd \$remoteHomeFolder && docker-compose up -d'"
				}

				register<UploadDockerCompose>("upload") {
					dependsOn(remoteDown)

					group = pluginGroup
					description =
						"Connects to the remote droplet and copies the 'docker-compose.yml' file to remoteHomeFolder"
				}

				register<UploadDockerCompose>("fullUpload") {
					dependsOn(arrayOf(remoteDown, *dependentModules))

					group = pluginGroup
					description =
						"Connects to the remote droplet and copies the 'docker-compose.yml' file after uploading the dependent modules images"
				}

			}
		}
	}

	companion object {

		private const val pluginGroup = "remote docker-compose"

	}

}

open class RemoteDockerComposeExtension {

	var dependentModules = emptyList<String>()

}
