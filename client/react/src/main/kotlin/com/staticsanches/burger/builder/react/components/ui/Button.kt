package com.staticsanches.burger.builder.react.components.ui

import com.staticsanches.burger.builder.react.hoc.defaultProps
import com.staticsanches.burger.builder.react.hoc.wrappedBy
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.RProps
import styled.StyleSheet
import styled.css
import styled.styledButton

enum class ButtonType {

	DANGER, SUCCESS

}

interface ButtonProps : RProps {

	var disabled: Boolean
	var buttonType: ButtonType
	var clicked: EventHandler

}

val button by FunctionalComponentDelegate<ButtonProps> { props ->
	styledButton {
		css {
			+ButtonStyles.button
			when (props.buttonType) {
				ButtonType.DANGER -> +ButtonStyles.danger
				ButtonType.SUCCESS -> +ButtonStyles.success
			}
		}
		attrs {
			disabled = props.disabled
			onClickFunction = props.clicked
		}

		props.children()
	}
} wrappedBy defaultProps {
	buttonType = ButtonType.SUCCESS
	disabled = false
}

private object ButtonStyles : StyleSheet("ButtonStyles") {

	val button by css {
		backgroundColor = Color.transparent
		border = "none"
		color = Color.white
		outline = Outline.none
		cursor = Cursor.pointer
		put("font", "inherit")
		padding(10.px)
		margin(10.px)
		fontWeight = FontWeight.bold

		firstOfType {
			marginLeft = 0.px
			paddingLeft = 0.px
		}

		disabled {
			color = Color("#ccc")
			cursor = Cursor.notAllowed
		}
	}

	val danger by css {
		color = Color("#944317")
	}

	val success by css {
		color = Color("#5C9210")
	}

}
