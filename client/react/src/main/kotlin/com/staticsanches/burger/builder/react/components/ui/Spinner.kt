package com.staticsanches.burger.builder.react.components.ui

import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.*
import kotlinx.css.properties.transform
import kotlinx.css.properties.translateZ
import react.RProps
import styled.StyleSheet
import styled.css
import styled.keyframesName
import styled.styledDiv

val spinner by FunctionalComponentDelegate<RProps> {
	styledDiv {
		css {
			+SpinnerStyles.loader
		}
	}
}

private object SpinnerStyles : StyleSheet("SpinnerStyles") {

	val common by css {
		borderRadius = 50.pct
		width = 2.5.em
		height = 2.5.em
		put("animation-fill-mode", "both")
		put("animation", "$load7 1.8s infinite ease-in-out")
	}

	val loader by css {
		+common

		color = Color("#0ca62b")
		fontSize = 10.px
		margin(80.px, LinearDimension.auto)
		position = Position.relative
		put("text-indent", "-9999em")
		transform {
			translateZ(0.px)
		}
		put("animation-delay", "-0.16s")

		val beforeAfter by css {
			content = "".quoted
			position = Position.absolute
			top = 0.px
		}
		before {
			+common
			+beforeAfter
			left = (-3.5).em
			put("animation-delay", "-0.32s")
		}
		after {
			+common
			+beforeAfter
			left = 3.5.em
		}
	}

	val load7 = keyframesName(
		"""
			0%,
			80%,
			100% {
				box-shadow: 0 2.5em 0 -1.3em;
			}
			40% {
				box-shadow: 0 2.5em 0 0;
			}
		""".trimIndent()
	)

}
