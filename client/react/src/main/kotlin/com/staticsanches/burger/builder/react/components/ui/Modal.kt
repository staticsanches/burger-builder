package com.staticsanches.burger.builder.react.components.ui

import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.BorderStyle
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.Position
import kotlinx.css.backgroundColor
import kotlinx.css.boxSizing
import kotlinx.css.left
import kotlinx.css.opacity
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.*
import kotlinx.css.px
import kotlinx.css.top
import kotlinx.css.vh
import kotlinx.css.width
import kotlinx.css.zIndex
import react.RProps
import styled.css
import styled.styledDiv

interface ModalProps : RProps {

	var show: Boolean
	var modalClosed: EventHandler

}

val modal by FunctionalComponentDelegate<ModalProps> { props ->
	backdrop {
		attrs {
			show = props.show
			clicked = props.modalClosed
		}
	}
	styledDiv {
		css {
			position = Position.fixed
			zIndex = 500
			backgroundColor = Color.white
			width = 70.pct
			border(1.px, BorderStyle.solid, Color("#ccc"))
			boxShadow(offsetX = 1.px, offsetY = 1.px, blurRadius = 1.px, color = Color.black)
			padding(16.px)
			left = 15.pct
			top = 30.pct
			boxSizing = BoxSizing.borderBox
			transition("all", 0.3.s, Timing.easeOut)

			media("(min-width: 600px)") {
				width = 500.px
				left = LinearDimension("calc(50% - 250px)")
			}

			transform {
				translateY(if (props.show) 0.px else (-100).vh)
			}
			opacity = if (props.show) 1 else 0
		}
		props.children()
	}
}
