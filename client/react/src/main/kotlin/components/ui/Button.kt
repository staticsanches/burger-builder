package components.ui

import hoc.defaultProps
import kotlinx.css.Color
import kotlinx.css.Cursor
import kotlinx.css.FontWeight
import kotlinx.css.Outline
import kotlinx.css.backgroundColor
import kotlinx.css.border
import kotlinx.css.color
import kotlinx.css.cursor
import kotlinx.css.fontWeight
import kotlinx.css.margin
import kotlinx.css.marginLeft
import kotlinx.css.outline
import kotlinx.css.padding
import kotlinx.css.paddingLeft
import kotlinx.css.px
import kotlinx.html.js.onClickFunction
import react.RProps
import styled.StyleSheet
import styled.css
import styled.styledButton
import utils.EventHandler
import utils.FunctionalComponent

enum class ButtonType {

	DANGER, SUCCESS

}

interface ButtonProps : RProps {

	var buttonType: ButtonType?
	var clicked: EventHandler
	var disabled: Boolean

}

val button by FunctionalComponent<ButtonProps>(
	defaultProps { disabled = false }
) { props ->
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
