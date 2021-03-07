package com.staticsanches.burger.builder.plugins

import com.staticsanches.burger.builder.plugins.tasks.docker.DockerBuild
import com.staticsanches.burger.builder.plugins.tasks.docker.UploadDockerImage
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

class RemoteDockerPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		val extension = target.extensions
			.create("remoteDocker", RemoteDockerExtension::class.java)

		target.afterEvaluate {
			extension.check()

			tasks {

				val dockerBuild by registering(DockerBuild::class) {
					dependsOn(named("build"))

					group = pluginGroup
					dockerTag = extension.dockerTag
					baseDirectory = extension.baseDirectory
				}

				register("uploadDockerImage", UploadDockerImage::class) {
					dependsOn(dockerBuild)

					group = pluginGroup
					dockerTag = extension.dockerTag

					doFirst {
						val useRemoteIP: String by project
						require(useRemoteIP.toBoolean()) { "Property 'useRemoteIP' must be set to true" }
					}
				}

			}
		}
	}

	companion object {

		private const val pluginGroup = "remote docker"

	}

}

open class RemoteDockerExtension {

	lateinit var dockerTag: String
	var baseDirectory = "."

	internal fun check() {
		if (!this::dockerTag.isInitialized) {
			throw RuntimeException(
				"""
					Please initialize the docker tag as follows:

					remoteDocker {
						dockerTag = "docker-tag"
					}
				""".trimIndent()
			)
		}
	}

}
