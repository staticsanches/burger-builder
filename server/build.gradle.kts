plugins {
	java
	application
	kotlin("jvm")
	id("com.github.johnrengelman.shadow")
	id("common-plugins")
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
	implementation(Dependencies.general.logback)
}

kotlin {

	target {

		attributes {
			attribute(androidAttribute, false)
			attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
		}

		compilations.all {
			kotlinOptions {
				useIR = true
				jvmTarget = "1.8"
				freeCompilerArgs = freeCompilerArgs + listOf(
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

// needed for shadow jar
setProperty("mainClassName", "io.ktor.server.netty.EngineMain")

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
