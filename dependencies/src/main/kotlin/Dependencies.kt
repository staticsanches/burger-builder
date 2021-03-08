@file:Suppress("UNUSED")

/**
 * Centralize all used dependencies.
 */
object Dependencies {

	val general = General
	val junit = Junit
	val kotlin = Kotlin
	val ktor = Ktor

	object Kotlin {

		val jsWrappers = JsWrappers
		val x = X

		object JsWrappers : DependencyGroup("org.jetbrains", "kotlin") {

			val react by dependency(Versions.kotlinReact)
			val reactDom by dependency(Versions.kotlinReact)
			val reactRouterDom by dependency(Versions.kotlinReactRouterDom)
			val redux by dependency(Versions.kotlinRedux)
			val reactRedux by dependency(Versions.kotlinReactRedux)
			val styled by dependency(Versions.kotlinStyled)

		}

		object X : DependencyGroup("org.jetbrains.kotlinx", "kotlinx") {

			val atomicfu by dependency(Versions.kotlinxAtomicfu)
			val collectionsImmutable by dependency(Versions.kotlinxCollectionsImmutable)
			val coroutines = Coroutines
			val datetime by dependency(Versions.kotlinxDatetime)
			val html = Html
			val serializationJson by dependency(Versions.kotlinxSerializationJson)

			object Coroutines :
				DependencyGroupWithVersion(X, "coroutines", Versions.kotlinxCoroutines) {

				val android by dependency()
				val core by dependency()
				val coreJs by dependency()

			}

			object Html : DependencyGroupWithVersion(X, "html", Versions.kotlinxHtml) {

				val js by dependency()
				val jvm by dependency()

			}

		}

	}

	object Ktor : DependencyGroupWithVersion("io.ktor", "ktor", Versions.ktor) {

		val auth by dependency()
		val authJwt by dependency()
		val serialization by dependency()
		val server by dependency()
		val serverNetty by dependency()

	}

	object General {

		val database = Database
		const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
		val terminal = Terminal

		object Database {

			const val flywayCore = "org.flywaydb:flyway-core:${Versions.flywaydb}"
			const val hikariCP = "com.zaxxer:HikariCP:${Versions.hikariCP}"
			const val postgresql = "org.postgresql:postgresql:${Versions.postgresql}"

		}

		object Terminal {

			const val progressbar = "me.tongfei:progressbar:${Versions.progressbar}"
			const val sshj = "com.hierynomus:sshj:${Versions.sshj}"

		}

	}

	object Junit {

		val jupiter = Jupiter

		object Jupiter : DependencyGroupWithVersion("org.junit.jupiter", "junit-jupiter", Versions.junit) {

			val api by dependency()
			val engine by dependency()

		}

	}

}
