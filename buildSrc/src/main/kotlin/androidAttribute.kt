import org.gradle.api.attributes.Attribute

val androidAttribute: Attribute<Boolean> =
	Attribute.of("com.staticsanches.android", Boolean::class.javaObjectType)
