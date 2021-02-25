package utils

import kotlinext.js.jsObject
import react.RBuilder
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.buildElement
import react.router.dom.RouteComponent
import react.router.dom.RouteProps
import react.router.dom.RouteResultProps

fun Number.toFixed(digits: Int): String =
	this.asDynamic().toFixed(digits) as String

fun <S : RState> RComponent<*, S>.setState(partialStateBuilder: S.() -> Unit) {
	val partialState = jsObject(partialStateBuilder)
	this.setState(partialState)
}

fun <P : RProps> RBuilder.route(
	path: String,
	component: RClass<P>,
	exact: Boolean = false,
	strict: Boolean = false
): ReactElement {
	return child<RouteProps<RProps>, RouteComponent<RProps>> {
		attrs {
			this.path = path
			this.exact = exact
			this.strict = strict
			this.component = component.unsafeCast<RClass<RProps>>()
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
