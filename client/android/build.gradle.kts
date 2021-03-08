import com.staticsanches.burger.builder.plugins.configurations.commonConfigurations

plugins {
	id("com.android.application")
	kotlin("android")
	id("dependencies")
	idea
}

android {

	commonConfigurations(project)

	defaultConfig {
		applicationId = "com.staticsanches.burger.builder.android"
	}

}

kotlin {

	target {
		commonConfigurations()
	}

	android.sourceSets.all {
		java.srcDir("src/$name/kotlin")
	}

}

dependencies {
	implementation(kotlin("stdlib"))

	implementation(project(":shared"))
	implementation(project(":client:commons"))

	implementation(Dependencies.kotlin.x.serializationJson)

	implementation("androidx.core:core-ktx:1.3.2")
	implementation("androidx.appcompat:appcompat:1.2.0")
	implementation("com.google.android.material:material:1.3.0")
	implementation("androidx.constraintlayout:constraintlayout:2.0.4")

	coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}

idea {
	module {
		name = "burger-builder-client-android"
	}
}
