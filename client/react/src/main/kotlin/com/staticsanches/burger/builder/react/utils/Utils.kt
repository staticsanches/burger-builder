package com.staticsanches.burger.builder.react.utils

import kotlinext.js.jsObject
import react.*
import react.router.dom.RouteComponent
import react.router.dom.RouteProps
import react.router.dom.RouteResultProps

fun RBuilder.route(
	path: String,
	component: RClass<RProps>,
	exact: Boolean = false,
	strict: Boolean = false
): ReactElement {
	return child<RouteProps<RProps>, RouteComponent<RProps>> {
		attrs {
			this.path = path
			this.exact = exact
			this.strict = strict
			this.component = component
		}
	}
}

fun RBuilder.route(
	path: String,
	exact: Boolean = false,
	strict: Boolean = false,
	render: (props: RouteResultProps<RProps>) -> ReactElement?
): ReactElement {
	return child<RouteProps<RProps>, RouteComponent<RProps>> {
		attrs {
			this.path = path
			this.exact = exact
			this.strict = strict
			this.render = render
		}
	}
}

fun <P : RProps> RClass<P>.rElement(props: P = jsObject()): ReactElement =
	buildElement { child(this@rElement, props) {} }
