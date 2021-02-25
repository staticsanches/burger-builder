package com.staticsanches.burger.builder.react.utils

import react.RBuilder
import react.RClass
import react.RProps
import react.rFunction
import kotlin.reflect.KProperty

/**
 * Creates a functional component, with the appropriate name.
 */
class FunctionalComponentDelegate<P : RProps>(
	private var name: String? = null,
	func: RBuilder.(props: P) -> Unit
) : RClassDelegate<P> {

	private var func: (RBuilder.(props: P) -> Unit)? = func
	private lateinit var value: RClass<P>

	override operator fun getValue(thisRef: Any?, property: KProperty<*>): RClass<P> {
		val func = this.func
		if (func != null) {
			this.func = null
			if (name == null) {
				name = property.name.capitalize()
			}
			value = rFunction(name!!, func)
		}
		return value
	}

	override fun toString(): String {
		return this.name ?: "Uninitialized Functional Component"
	}

}
