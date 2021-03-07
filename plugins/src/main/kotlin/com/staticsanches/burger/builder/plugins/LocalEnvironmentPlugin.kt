package com.staticsanches.burger.builder.plugins

import com.staticsanches.burger.builder.plugins.tasks.db.CleanDatabase
import com.staticsanches.burger.builder.plugins.tasks.docker.DockerComposeDown
import com.staticsanches.burger.builder.plugins.tasks.docker.DockerComposeUp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

/**
 * Plugin that register tasks to manipulate the local environment.
 */
class LocalEnvironmentPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		val extension = target.extensions
			.create("localEnvironment", LocalEnvironmentExtension::class.java)

		target.afterEvaluate {
			extension.check()

			tasks {

				val startLocalEnvironment by registering(DockerComposeUp::class) {
					group = pluginGroup
					baseDirectory = extension.baseDirectory
				}

				register("stopLocalEnvironment", DockerComposeDown::class) {
					group = pluginGroup
					baseDirectory = extension.baseDirectory
				}

				register("cleanLocalDatabase", CleanDatabase::class) {
					mustRunAfter(startLocalEnvironment)

					group = pluginGroup
					driver = extension.databaseInfo.driver
					url = extension.databaseInfo.url
					user = extension.databaseInfo.user
					password = extension.databaseInfo.password
				}

			}
		}
	}

	companion object {

		private const val pluginGroup = "local environment"

	}

}

open class LocalEnvironmentExtension {

	lateinit var baseDirectory: String

	internal val databaseInfo = DatabaseInfo()

	fun database(block: DatabaseInfo.() -> Unit) {
		databaseInfo.block()
	}

	internal fun check() {
		if (!this::baseDirectory.isInitialized) {
			throw RuntimeException(
				"""
					Please initialize the base directory as follows:

					localEnvironment {
						baseDirectory = "path-to-base-directory"
					}
				""".trimIndent()
			)
		}
		databaseInfo.check()
	}

	class DatabaseInfo internal constructor() {

		lateinit var driver: String
		lateinit var url: String
		lateinit var user: String
		lateinit var password: String

		internal fun check() {
			if (
				this::driver.isInitialized &&
				this::url.isInitialized &&
				this::user.isInitialized &&
				this::password.isInitialized
			) {
				return // it is filled
			}
			throw RuntimeException(
				"""
					Please initialize the database as follows:

					localEnvironment {
						database {
							driver = "database-driver"
							url = "database-url"
							user = "database-user"
							password = "database-password"
						}
					}
				""".trimIndent()
			)
		}

	}

}
