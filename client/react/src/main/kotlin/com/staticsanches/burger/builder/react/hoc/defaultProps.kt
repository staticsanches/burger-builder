package com.staticsanches.burger.builder.react.hoc

import kotlinext.js.jsObject
import react.RProps

/**
 * [HOC] to allow the definition of default props for com.staticsanches.burger.builder.react.components.
 */
fun <P : RProps> defaultProps(builder: P.() -> Unit): HOC<P, P> {
	val defaultProps = jsObject(builder)
	return {
		it.asDynamic().defaultProps = defaultProps
		it
	}
}
