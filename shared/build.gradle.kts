import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import java.net.InetAddress

plugins {
	id("mpp-android-library")
	id("mpp-js-library")
	id("com.codingfeline.buildkonfig")
	idea
}

kotlin {

	targets {
		jvm {
			attributes {
				attribute(androidAttribute, false)
				attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 8)
				attribute(KotlinPlatformType.attribute, KotlinPlatformType.jvm)
			}
			compilations.all {
				kotlinOptions {
					jvmTarget = "1.8"
				}
				tasks.withType<Test> {
					useJUnitPlatform()
				}
			}
		}
	}

	sourceSets {

		val commonMain by getting {
			dependencies {
				implementation(Dependencies.kotlin.x.serializationJson)
				implementation(Dependencies.kotlin.x.collectionsImmutable)
			}
		}

		val commonTest by getting {
			dependencies {
				implementation(kotlin("test"))
				implementation(kotlin("test-annotations-common"))
			}
		}

		val javaShared by creating {
			dependsOn(commonMain)
		}

		getByName("jvmMain") {
			dependsOn(javaShared)
		}

		getByName("jvmTest") {
			dependsOn(commonTest)

			dependencies {
				implementation(kotlin("test-junit5"))

				implementation(Dependencies.junit.jupiter.api)
				runtimeOnly(Dependencies.junit.jupiter.engine)
			}
		}

		getByName("androidMain") {
			dependsOn(javaShared)
		}

	}

}

buildkonfig {
	packageName = "com.staticsanches.burger.builder.shared"

	val apiPort: String by project
	val useRemoteIP: String by project
	val apiIP: String = if (useRemoteIP.toBoolean()) {
		val remoteIP: String by project
		remoteIP
	} else {
		InetAddress.getLocalHost().hostAddress
	}

	val apiBaseUrl = "http://$apiIP:$apiPort"
	defaultConfigs {
		buildConfigField(
			com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
			"apiBaseUrl",
			apiBaseUrl
		)
	}
}

idea {
	module {
		name = "burger-builder-shared"
	}
}
