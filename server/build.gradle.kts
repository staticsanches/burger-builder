plugins {
	java
	application
	kotlin("jvm")
	id("kotlin-kapt")
	kotlin("plugin.serialization")
	id("com.github.johnrengelman.shadow")
	id("net.saliman.properties")
	id("dependencies")
	id("remote-docker")
	id("local-environment")
	idea
}

dependencies {
	implementation(project(":shared"))

	implementation(Dependencies.ktor.auth)
	implementation(Dependencies.ktor.authJwt)
	implementation(Dependencies.ktor.serialization)
	implementation(Dependencies.ktor.server)
	implementation(Dependencies.ktor.serverNetty)
	implementation("ch.qos.logback:logback-classic:${Versions.logback}")
}

kotlin {

	target {

		attributes {
			attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
			attribute(androidAttribute, false)
		}

		compilations.all {
			kotlinOptions {
				//useIR = true
				jvmTarget = "1.8"
				freeCompilerArgs = freeCompilerArgs + listOf(
					"-XXLanguage:+InlineClasses",
					"-Xopt-in=kotlin.RequiresOptIn",
					"-Xopt-in=kotlin.time.ExperimentalTime",
					"-Xopt-in=io.ktor.util.KtorExperimentalAPI",
					"-Xopt-in=kotlin.contracts.ExperimentalContracts",
					"-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
				)
			}
		}

	}

}

remoteDocker {
	dockerTag = "burger-builder/server"
}

localEnvironment {
	baseDirectory = "local-environment"

	database {
		driver = "org.postgresql.Driver"
		url = "jdbc:postgresql://localhost:54321/burger-builder"
		user = "burger-builder"
		password = "burger-builder"
	}
}

application {
	@Suppress("DEPRECATION") // needed because of shadow jar
	mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks {

	test {
		useJUnitPlatform()
	}

	shadowJar {
		archiveBaseName.set("burger-builder-server")
		archiveClassifier.set("")
		archiveVersion.set("")
		mergeServiceFiles()
	}

}

idea {
	module {
		name = "burger-builder-server"
	}
}
