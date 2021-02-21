package utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val DefaultJson = Json {
	encodeDefaults = true
	isLenient = true
	allowSpecialFloatingPointValues = true
	allowStructuredMapKeys = true
	prettyPrint = true
	useArrayPolymorphism = true
	ignoreUnknownKeys = true
}

inline fun <reified T> String.fromJson(): T =
	DefaultJson.decodeFromString(this)

inline fun <reified T> T.toJson(): String =
	DefaultJson.encodeToString(this)

inline fun <reified T> Any.convertFromJson(): T =
	JSON.stringify(this).fromJson()
