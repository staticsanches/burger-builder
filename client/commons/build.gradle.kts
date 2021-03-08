plugins {
	id("mpp-android-library")
	id("mpp-js-library")
	id("com.codingfeline.buildkonfig")
	idea
}

kotlin {

	sourceSets {

		val commonMain by getting {
			dependencies {
				implementation(project(":shared"))
			}
		}

		commonTest {
			dependencies {
				implementation(kotlin("test"))
				implementation(kotlin("test-annotations-common"))
			}
		}

		getByName("androidMain") {
			dependsOn(commonMain)
		}

	}

}

buildkonfig {
	packageName = "com.staticsanches.burger.builder.client.commons"

	val firebaseUrl: String by project
	defaultConfigs {
		buildConfigField(
			com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
			"firebaseUrl",
			firebaseUrl
		)
	}
}

idea {
	module {
		name = "burger-builder-client-commons"
	}
}
