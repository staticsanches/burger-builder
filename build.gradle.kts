plugins {
	id("close-ssh")
	id("check-kotlin-version")
	idea
}

buildscript {

	repositories {
		google()
		jcenter()
		mavenCentral()
		maven(url = "https://maven.google.com")
	}

	val kotlinVersion = "1.4.30"
	dependencies {
		classpath("com.android.tools.build:gradle:4.1.2")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
		classpath("org.jetbrains.kotlin:kotlin-serialization:${kotlinVersion}")
		classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.7.0")
		classpath("net.saliman:gradle-properties-plugin:1.5.1")
		classpath("com.github.jengelman.gradle.plugins:shadow:6.1.0")
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
		maven(url = "https://dl.bintray.com/kotlin/exposed")
		maven(url = "https://dl.bintray.com/kotlin/kotlin-dev")
		maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
		maven(url = "https://dl.bintray.com/kotlin/kotlin-js-wrappers")
		maven(url = "https://dl.bintray.com/kotlin/kotlinx")
		maven(url = "https://dl.bintray.com/kotlin/ktor")
		maven(url = "https://kotlin.bintray.com/kotlinx/")
	}

}

idea {
	module {
		name = "burger-builder"
		excludeDirs = excludeDirs + file(".idea") + file("gradle")
	}
}
