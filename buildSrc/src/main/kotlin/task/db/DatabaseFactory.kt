package task.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway

internal class DatabaseFactory(
	driver: String, url: String, user: String, password: String
) : AutoCloseable {

	private val dataSource: HikariDataSource

	init {
		val config = HikariConfig()
		config.driverClassName = driver
		config.jdbcUrl = url
		config.username = user
		config.password = password
		config.maximumPoolSize = 3
		config.isAutoCommit = false
		config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
		config.initializationFailTimeout = 0
		config.validate()
		dataSource = HikariDataSource(config)
	}

	fun configure(setup: Flyway.() -> Unit) {
		setup(Flyway.configure().dataSource(dataSource).load())
	}

	override fun close() {
		dataSource.close()
	}

}
