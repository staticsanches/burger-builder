plugins {
	java
	application
	kotlin("jvm")
	id("kotlin-kapt")
	kotlin("plugin.serialization") version Versions.kotlin
	id("com.github.johnrengelman.shadow") version Versions.shadow
	id("net.saliman.properties") version Versions.salimanGradleProperties
	idea
}

kotlin {

	target {

		attributes {
			attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
			attribute(androidAttribute, false)
		}

		compilations.all {
			kotlinOptions {
				useIR = true
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

dependencies {
	implementation(project(":shared"))

	implementation("io.ktor:ktor-auth:${Versions.ktor}")
	implementation("io.ktor:ktor-auth-jwt:${Versions.ktor}")
	implementation("io.ktor:ktor-serialization:${Versions.ktor}")
	implementation("io.ktor:ktor-server:${Versions.ktor}")
	implementation("io.ktor:ktor-server-netty:${Versions.ktor}")
	implementation("ch.qos.logback:logback-classic:${Versions.logback}")
}

application {
	@Suppress("DEPRECATION") // needed because of shadow jar
	mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks {

	val dockerBuild by registering(task.DockerBuild::class) {
		dependsOn(build)
	}

	register("uploadDockerImage", task.UploadDockerImage::class) {
		dependsOn(dockerBuild)
		doFirst {
			val useRemoteIP: String by project
			require(useRemoteIP.toBoolean()) { "Property 'useRemoteIP' must be set to true" }
		}
	}

	val startLocalEnvironment by registering(task.DockerComposeUp::class) {
		baseDirectory = "local-environment"
	}

	register("stopLocalEnvironment", task.DockerComposeDown::class) {
		baseDirectory = "local-environment"
	}

	register("cleanLocalDatabase", task.db.CleanDatabase::class) {
		dependsOn(startLocalEnvironment)

		driver = "org.postgresql.Driver"
		url = "jdbc:postgresql://localhost:54321/burger-builder"
		user = "burger-builder"
		password = "burger-builder"
	}

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
