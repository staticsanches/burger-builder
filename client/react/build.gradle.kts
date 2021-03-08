import com.staticsanches.burger.builder.plugins.configurations.commonConfigurations

plugins {
	kotlin("js")
	id("common-plugins")
	id("remote-docker")
	idea
}

dependencies {
	implementation(project(":shared"))
	implementation(project(":client:commons"))

	implementation(Dependencies.kotlin.x.serializationJson)
	implementation(Dependencies.kotlin.x.collectionsImmutable)

	implementation(Dependencies.kotlin.jsWrappers.react)
	implementation(Dependencies.kotlin.jsWrappers.reactDom)
	implementation(Dependencies.kotlin.jsWrappers.reactRouterDom)
	implementation(Dependencies.kotlin.jsWrappers.redux)
	implementation(Dependencies.kotlin.jsWrappers.reactRedux)
	implementation(Dependencies.kotlin.jsWrappers.styled)

	implementation(npm("axios", Versions.axios))
	implementation(npm("react", Versions.react))
	implementation(npm("react-is", Versions.react))
	implementation(npm("react-dom", Versions.react))
	implementation(npm("react-router-dom", Versions.reactRouterDom))
	implementation(npm("redux", Versions.redux))
	implementation(npm("react-redux", Versions.reactRedux))
	implementation(npm("styled-components", Versions.styled))
}

kotlin {

	js {
		commonConfigurations()
	}

	sourceSets.all {
		languageSettings.apply {
			progressiveMode = true
			apiVersion = "1.4"
			languageVersion = "1.4"
			enableLanguageFeature("InlineClasses")
			useExperimentalAnnotation("kotlin.RequiresOptIn")
			useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
		}
	}

}

remoteDocker {
	dockerTag = "burger-builder/client"
}

idea {
	module {
		name = "burger-builder-client-react"
	}
}
