package com.staticsanches.burger.builder.react.utils

import kotlinext.js.jsObject
import react.RComponent
import react.RState


fun <S : RState> RComponent<*, S>.setState(partialStateBuilder: S.() -> Unit) {
	val partialState = jsObject(partialStateBuilder)
	this.setState(partialState)
}
