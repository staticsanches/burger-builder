plugins {
	`kotlin-dsl`
	`java-gradle-plugin`
}

group = "com.staticsanches.burger.builder.dependencies"
version = "0.0.1-SNAPSHOT"

repositories {
	google()
	jcenter()
	mavenCentral()
	maven(url = "https://maven.google.com")
}

gradlePlugin {
	plugins.register("dependencies") {
		id = "dependencies"
		implementationClass = "com.staticsanches.burger.builder.dependencies.DependenciesPlugin"
	}
}
