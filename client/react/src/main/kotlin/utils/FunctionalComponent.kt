package utils

import hoc.HOC
import react.RBuilder
import react.RClass
import react.RProps
import react.rFunction
import kotlin.reflect.KProperty

/**
 * Creates a functional component, wrapping it with the optional [HOC].
 */
class FunctionalComponent<P : RProps>(
	private var hoc: HOC<P>? = null,
	func: RBuilder.(props: P) -> Unit
) {

	private var func: (RBuilder.(props: P) -> Unit)? = func
	private lateinit var value: RClass<P>

	operator fun getValue(thisRef: Any?, property: KProperty<*>): RClass<P> {
		val func = this.func
		if (func != null) {
			val hoc = hoc
			this.func = null
			this.hoc = null
			val value = rFunction(property.name.capitalize(), func)
			this.value = if (hoc == null) value else hoc(value)
		}
		return value
	}

}
