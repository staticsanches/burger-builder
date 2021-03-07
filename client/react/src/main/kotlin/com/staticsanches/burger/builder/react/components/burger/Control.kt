package com.staticsanches.burger.builder.react.components.burger

import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import com.staticsanches.burger.builder.shared.js.utils.toFixed
import kotlinx.css.Align
import kotlinx.css.BorderStyle
import kotlinx.css.Color
import kotlinx.css.Cursor
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.FontWeight
import kotlinx.css.JustifyContent
import kotlinx.css.LinearDimension
import kotlinx.css.Outline
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.color
import kotlinx.css.cursor
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.fontWeight
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.outline
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.*
import kotlinx.css.px
import kotlinx.css.width
import kotlinx.html.js.onClickFunction
import react.RProps
import react.dom.p
import react.dom.strong
import styled.*

interface BuildControlsProps : RProps {

	var price: Number
	var purchasable: Boolean
	var ordered: EventHandler
	var disabled: (IngredientType) -> Boolean
	var lessHandler: (IngredientType) -> Unit
	var moreHandler: (IngredientType) -> Unit

}

val buildControls by FunctionalComponentDelegate<BuildControlsProps> { props ->
	styledDiv {
		css { +ControlStyles.buildControls }
		p {
			+"Current Price: "
			strong {
				+"\$${props.price.toFixed(2)}"
			}
		}
		IngredientType.configurableValues
			.renderEach { ingredient ->
				control {
					attrs {
						key = ingredient.name
						label = ingredient.label
						lessDisabled = props.disabled(ingredient)
						this.lessHandler = { props.lessHandler(ingredient) }
						this.moreHandler = { props.moreHandler(ingredient) }
					}
				}
			}

		styledButton {
			css { +ControlStyles.orderButton }
			attrs {
				this.disabled = !props.purchasable
				onClickFunction = props.ordered
			}
			+"ORDER NOW"
		}
	}
}

private interface ControlProps : RProps {

	var label: String
	var lessDisabled: Boolean
	var lessHandler: EventHandler
	var moreHandler: EventHandler

}

private val control by FunctionalComponentDelegate<ControlProps> { props ->
	styledDiv {
		css { +ControlStyles.buildControl }
		styledDiv {
			css { +ControlStyles.label }
			+props.label
		}
		styledButton {
			css { +ControlStyles.less }
			attrs {
				disabled = props.lessDisabled
				onClickFunction = props.lessHandler
			}
			+"Less"
		}
		styledButton {
			css { +ControlStyles.more }
			attrs.onClickFunction = props.moreHandler
			+"More"
		}
	}
}

private object ControlStyles : StyleSheet("ControlStyles") {

	val buildControls by css {
		width = 100.pct
		backgroundColor = Color("#CF8F2E")
		display = Display.flex
		flexDirection = FlexDirection.column
		alignItems = Align.center
		boxShadow(offsetX = 0.px, offsetY = 2.px, blurRadius = 1.px, color = Color("#ccc"))
		margin(LinearDimension.auto)
		padding(10.px, 0.px)
	}

	val orderButton by css {
		backgroundColor = Color("#DAD735")
		outline = Outline.none
		cursor = Cursor.pointer
		border(1.px, BorderStyle.solid, Color("#966909"))
		color = Color("#966909")
		fontFamily = "inherit"
		fontSize = 1.2.em
		padding(15.px, 30.px)
		boxShadow(Color("#966909"), 2.px, 2.px, 2.px)

		val hoverActive by css {
			backgroundColor = Color("#A0DB41")
			border(1.px, BorderStyle.solid, Color("#966909"))
			color = Color("#966909")
		}
		hover(hoverActive)
		active(hoverActive)

		disabled {
			backgroundColor = Color("#C7C6C6")
			cursor = Cursor.notAllowed
			border(1.px, BorderStyle.solid, Color("#ccc"))
			color = Color("#888888")
		}

		val enable = keyframes {
			0 {
				transform { scale(1) }
			}
			60 {
				transform { scale(1.1) }
			}
			100 {
				transform { scale(1) }
			}
		}

		not(":disabled") {
			animation(enable, 0.3.s, Timing.linear)
		}
	}

	val buildControl by css {
		display = Display.flex
		justifyContent = JustifyContent.spaceBetween
		alignItems = Align.center
		margin(5.px, 0.px)

		descendants("button") {
			display = Display.block
			put("font", "inherit")
			padding(5.px)
			margin(0.px, 5.px)
			width = 80.px
			border(width = 1.px, style = BorderStyle.solid, color = Color("#AA6817"))
			cursor = Cursor.pointer
			outline = Outline.none

			disabled {
				backgroundColor = Color("#AC9980")
				border(width = 1.px, style = BorderStyle.solid, color = Color("#7E7365"))
				color = Color("#ccc")
				cursor = Cursor.default

				hover {
					backgroundColor = Color("#AC9980")
					color = Color("#ccc")
					cursor = Cursor.notAllowed
				}
			}
		}

	}

	val label by css {
		padding(10.px)
		fontWeight = FontWeight.bold
		width = 80.px
	}

	val less by css {
		backgroundColor = Color("#D39952")
		color = Color.white

		val hoverActive by css {
			backgroundColor = Color("#DAA972")
			color = Color.white
		}
		hover(hoverActive)
		active(hoverActive)
	}

	val more by css {
		backgroundColor = Color("#8F5E1E")
		color = Color.white

		val hoverActive by css {
			backgroundColor = Color("#99703F")
			color = Color.white
		}
		hover(hoverActive)
		active(hoverActive)
	}

}
