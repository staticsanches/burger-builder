package utils

import hoc.HOC
import hoc.hocDisplayName
import react.RBuilder
import react.RClass
import react.RProps
import react.buildElements
import kotlin.reflect.KProperty


/**
 * Creates a functional HOC.
 */
class FunctionalHOC<P : RProps>(func: RBuilder.(component: RClass<P>, props: P) -> Unit) {

	private var func: (RBuilder.(component: RClass<P>, props: P) -> Unit)? = func
	private lateinit var hoc: HOC<P>

	operator fun getValue(thisRef: Any?, property: KProperty<*>): HOC<P> {
		val func = func
		if (func != null) {
			this.func = null
			hoc = hoc@{ component ->
				return@hoc { props: P ->
					buildElements { func(component, props) }
				}.unsafeCast<RClass<P>>()
					.also {
						it.displayName = property.hocDisplayName(component)
					}
			}
		}
		return hoc
	}

}
