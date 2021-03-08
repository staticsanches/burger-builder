package com.staticsanches.burger.builder.plugins

import com.staticsanches.burger.builder.plugins.configurations.commonConfigurations
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MppCommonConfigsPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		with(target) {
			apply(plugin = "org.jetbrains.kotlin.multiplatform")
			apply(plugin = "common-plugins")

			extensions.configure(KotlinMultiplatformExtension::class.java) {
				targets.all {
					compilations.all {
						kotlinOptions {
							commonConfigurations()
						}
					}
				}

				sourceSets.all {
					commonConfigurations()
				}
			}
		}
	}

}
