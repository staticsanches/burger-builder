buildscript {

	repositories {
		google()
		mavenCentral()
		jcenter()
		maven(url = "https://maven.google.com")
		maven(url = "http://jcenter.bintray.com/")
	}

	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
		classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:${Versions.buildKonfig}")
	}

}

allprojects {

	group = "com.staticsanches.burger.builder"
	version = "0.0.1-SNAPSHOT"

	repositories {
		google()
		jcenter()
		mavenCentral()
		maven(url = "https://maven.google.com")
		maven(url = "http://jcenter.bintray.com/")
		maven(url = "https://dl.bintray.com/kotlin/exposed")
		maven(url = "https://dl.bintray.com/kotlin/kotlin-dev")
		maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
		maven(url = "https://dl.bintray.com/kotlin/kotlin-js-wrappers")
		maven(url = "https://dl.bintray.com/kotlin/kotlinx")
		maven(url = "https://dl.bintray.com/kotlin/ktor")
		maven(url = "https://kotlin.bintray.com/kotlinx/")
	}

}

gradle.buildFinished {
	task.closeSsh()
}
