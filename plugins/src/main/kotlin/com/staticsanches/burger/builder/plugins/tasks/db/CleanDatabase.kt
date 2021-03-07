package com.staticsanches.burger.builder.plugins.tasks.db

import org.flywaydb.core.Flyway
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

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
