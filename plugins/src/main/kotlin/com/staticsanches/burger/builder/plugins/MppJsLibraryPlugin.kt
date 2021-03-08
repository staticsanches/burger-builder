package com.staticsanches.burger.builder.plugins

import com.staticsanches.burger.builder.plugins.configurations.commonConfigurations
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MppJsLibraryPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		with(target) {
			apply(plugin = "mpp-common-configs")

			extensions.configure(KotlinMultiplatformExtension::class.java) {
				js {
					commonConfigurations()
				}

				sourceSets {
					getByName("jsMain") {
						dependsOn(getByName("commonMain"))
					}

					getByName("jsTest") {
						dependsOn(getByName("commonTest"))

						dependencies {
							implementation(kotlin("test-js"))
						}
					}
				}
			}
		}
	}

}
