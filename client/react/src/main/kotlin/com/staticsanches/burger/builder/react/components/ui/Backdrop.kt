package com.staticsanches.burger.builder.react.components.ui

import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.RProps
import styled.css
import styled.styledDiv

interface BackdropProps : RProps {

	var show: Boolean
	var clicked: EventHandler

}

val backdrop by FunctionalComponentDelegate<BackdropProps> { props ->
	if (props.show) {
		styledDiv {
			css {
				width = 100.pct
				height = 100.pct
				position = Position.fixed
				zIndex = 100
				left = 0.px
				top = 0.px
				backgroundColor = rgba(0, 0, 0, 0.5)
			}
			attrs.onClickFunction = props.clicked
		}
	}
}
