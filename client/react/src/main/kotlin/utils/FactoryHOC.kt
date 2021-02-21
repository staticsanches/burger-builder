package utils

import hoc.HOC
import hoc.hocDisplayName
import react.Component
import react.RClass
import react.RComponent
import react.RProps
import kotlin.reflect.KProperty

/**
 * Since Kotlin does not allow return of class, uses the "factory" component that is supported by React.
 *
 * Attention!!! Use initialProps only to initialize the component.
 */
class FactoryHOC<P : RProps>(factory: (component: RClass<P>, initialProps: P) -> RComponent<P, *>) {

	private var factory: ((component: RClass<P>, props: P) -> RComponent<P, *>)? = factory
	private lateinit var hoc: HOC<P>

	operator fun getValue(thisRef: Any?, property: KProperty<*>): HOC<P> {
		val factory = factory
		if (factory != null) {
			this.factory = null
			hoc = hoc@{ component ->
				return@hoc { props: P ->
					factory(component, props)
				}.unsafeCast<RClass<P>>()
					.also {
						it.displayName = property.hocDisplayName(component)
						// Workaround so React does not complain about object being returned
						// https://reactjs.org/blog/2019/08/08/react-v16.9.0.html
						it.asDynamic().prototype = Component::class.js.asDynamic().prototype
					}
			}
		}
		return hoc
	}

}
