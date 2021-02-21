package hoc

import react.RClass
import react.RProps
import kotlin.reflect.KProperty

/**
 * It is considered a best practice to return a [RClass] with the display name that is like HOC(original component name).
 */
typealias HOC<P> = (RClass<P>) -> RClass<P>

fun hocDisplayName(hoc: String, component: RClass<*>): String =
	hoc.capitalize() + "(" + component.displayName + ")"

fun KProperty<*>.hocDisplayName(component: RClass<*>): String =
	hocDisplayName(this.name, component)

/**
 * Combines multiple [HOC]s.
 */
fun <P : RProps> allOf(vararg hocs: HOC<P>): (component: RClass<P>) -> RClass<P> =
	{ hocs.fold(it) { acc, hoc -> hoc(acc) } }
