@file:Suppress("UNUSED")

/**
 * Centralize all used versions.
 */
object Versions {

	const val kotlin = "1.4.30"

	// kotlin-js-wrappers
	private const val kotlinJsWrappersBuild = "pre.148-kotlin-$kotlin"

	const val androidPlugin = "4.1.2"

	// kotlinx
	const val kotlinxAtomicfu = "0.15.0"
	const val kotlinxCollectionsImmutable = "0.3.3"
	const val kotlinxCoroutines = "1.4.2"
	const val kotlinxDatetime = "0.1.1"
	const val kotlinxHtml = "0.7.2"
	const val kotlinxSerializationJson = "1.1.0"

	// js
	const val axios = "0.21.1"
	const val muirwik = "0.6.3"
	const val react = "17.0.1"
	const val kotlinReact = "$react-$kotlinJsWrappersBuild"
	const val reactRouterDom = "5.2.0"
	const val kotlinReactRouterDom = "$reactRouterDom-$kotlinJsWrappersBuild"
	const val redux = "4.0.5"
	const val kotlinRedux = "$redux-$kotlinJsWrappersBuild"
	const val reactRedux = "7.2.2"
	const val kotlinReactRedux = "$reactRedux-$kotlinJsWrappersBuild"
	const val styled = "5.2.1"
	const val kotlinStyled = "$styled-$kotlinJsWrappersBuild"
	const val uuid = "8.3.2"

	// general
	const val kodein = "7.3.1"
	const val ktor = "1.5.1"
	const val logback = "1.2.3"
	const val okio = "2.10.0"
	const val okhttp = "4.9.1"

	// terminal
	const val sshj = "0.31.0"
	const val progressbar = "0.9.0"

	// database
	const val exposed = "0.29.1"
	const val flywaydb = "7.5.3"
	const val hikariCP = "4.0.2"
	const val postgresql = "42.2.19"

	// test
	const val junit = "5.2.0"
	const val kotest = "4.4.1"

}
