rootProject.name = "burger-builder"

pluginManagement {
	repositories {
		google()
		jcenter()
		gradlePluginPortal()
	}
}

includeBuild("dependencies")
includeBuild("plugins")

include(":shared")
include(":server")
include(":client", ":client:commons", ":client:android", ":client:react")
