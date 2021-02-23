package hoc

import kotlinext.js.jsObject
import react.RProps

/**
 * [HOC] to allow the definition of default props for components.
 */
fun <P : RProps> defaultProps(builder: P.() -> Unit): HOC<P> {
	val defaultProps = jsObject(builder)
	return {
		it.asDynamic().defaultProps = defaultProps
		it
	}
}