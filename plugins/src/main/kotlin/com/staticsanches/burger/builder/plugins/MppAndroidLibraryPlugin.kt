package com.staticsanches.burger.builder.plugins

import Dependencies
import com.android.build.gradle.LibraryExtension
import com.staticsanches.burger.builder.plugins.configurations.commonConfigurations
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("UnstableApiUsage")
class MppAndroidLibraryPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		with(target) {
			apply(plugin = "mpp-common-configs")
			apply(plugin = "com.android.library")

			extensions.configure(LibraryExtension::class.java) {
				commonConfigurations(this@with)

				sourceSets {
					getByName("main") {
						manifest.srcFile("src/androidMain/AndroidManifest.xml")
						java.srcDirs("src/androidMain/kotlin")
						res.srcDirs("src/androidMain/res")
					}
					getByName("androidTest") {
						java.srcDirs("src/androidTest/kotlin")
						res.srcDirs("src/androidTest/res")
					}
				}
			}

			dependencies {
				add("androidTestImplementation", "com.android.support.test:runner:1.0.2")
				add("coreLibraryDesugaring", "com.android.tools:desugar_jdk_libs:1.1.5")
			}

			extensions.configure(KotlinMultiplatformExtension::class.java) {
				android {
					commonConfigurations()
				}

				sourceSets {
					getByName("androidTest") {
						dependsOn(getByName("commonTest"))
						dependsOn(getByName("androidAndroidTestRelease"))

						dependencies {
							implementation(kotlin("test-junit5"))

							implementation(Dependencies.junit.jupiter.api)
							runtimeOnly(Dependencies.junit.jupiter.engine)
						}
					}
				}
			}
		}
	}

}
