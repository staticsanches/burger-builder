plugins {
	`kotlin-dsl`
	`java-gradle-plugin`
	id("dependencies")
}

group = "com.staticsanches.burger.builder.plugins"
version = "0.0.1-SNAPSHOT"

repositories {
	google()
	jcenter()
	mavenCentral()
	maven(url = "https://maven.google.com")
}

dependencies {
	implementation(kotlin("stdlib"))

	implementation(Dependencies.general.terminal.progressbar)
	implementation(Dependencies.general.terminal.sshj)
	implementation(Dependencies.general.database.flywayCore)
	implementation(Dependencies.general.database.hikariCP)
	implementation(Dependencies.general.database.postgresql)

	// Plugins
	compileOnly("com.staticsanches.burger.builder.dependencies:dependencies:${version}")
	compileOnly(Dependencies.android.gradlePlugin)
	compileOnly(Dependencies.kotlin.gradlePlugin)
}

gradlePlugin {
	plugins {

		register("close-ssh") {
			id = "close-ssh"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.CloseSshPlugin"
		}

		register("local-environment") {
			id = "local-environment"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.LocalEnvironmentPlugin"
		}

		register("remote-docker") {
			id = "remote-docker"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.RemoteDockerPlugin"
		}

		register("remote-docker-compose") {
			id = "remote-docker-compose"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.RemoteDockerComposePlugin"
		}

		register("mpp-common-configs") {
			id = "mpp-common-configs"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.MppCommonConfigsPlugin"
		}

		register("mpp-android-library") {
			id = "mpp-android-library"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.MppAndroidLibraryPlugin"
		}

		register("mpp-js-library") {
			id = "mpp-js-library"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.MppJsLibraryPlugin"
		}

		register("common-plugins") {
			id = "common-plugins"
			implementationClass =
				"com.staticsanches.burger.builder.plugins.CommonPluginsPlugin"
		}

	}
}
