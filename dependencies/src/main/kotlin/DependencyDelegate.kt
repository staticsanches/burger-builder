import java.util.Locale
import kotlin.reflect.KProperty

internal interface DependencyDelegate {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): String

}

private val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
internal fun String.toKebabCase(): String {
	return camelRegex.replace(this) {
		"-${it.value}"
	}.toLowerCase(Locale.ROOT)
}
