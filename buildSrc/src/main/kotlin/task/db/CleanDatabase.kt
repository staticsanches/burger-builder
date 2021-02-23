package task.db

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.flywaydb.core.Flyway

/**
 * Using [Flyway], cleans the informed database.
 */
open class CleanDatabase : DefaultTask() {

	@Input lateinit var driver: String
	@Input lateinit var url: String
	@Input lateinit var user: String
	@Input lateinit var password: String

	@TaskAction
	fun run() {
		DatabaseFactory(driver, url, user, password).use {
			it.configure { clean() }
		}
	}

}