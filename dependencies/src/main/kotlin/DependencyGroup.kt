import kotlin.reflect.KProperty

/**
 * Represents a group of dependencies that share the same group and a prefix name.
 */
abstract class DependencyGroup internal constructor(
	internal val group: String, internal val prefix: String
) {

	internal fun dependency(version: String): DependencyDelegate =
		object : DependencyDelegate {


			override operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
				val name = prefix + "-" + property.name.toKebabCase()
				return "$group:$name:$version"
			}

		}

}

/**
 * Represents a group of dependencies that share the same group and a prefix name and a version.
 */
abstract class DependencyGroupWithVersion internal constructor(
	group: String, prefix: String, private val version: String
) : DependencyGroup(group, prefix) {

	constructor(parent: DependencyGroup, prefix: String, version: String) : this(
		parent.group, parent.prefix + "-" + prefix, version
	)

	internal fun dependency(): DependencyDelegate =
		dependency(version)

}
