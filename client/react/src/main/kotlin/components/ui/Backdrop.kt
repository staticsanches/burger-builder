package components.ui

import kotlinx.css.Position
import kotlinx.css.backgroundColor
import kotlinx.css.height
import kotlinx.css.left
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.px
import kotlinx.css.rgba
import kotlinx.css.top
import kotlinx.css.width
import kotlinx.css.zIndex
import kotlinx.html.js.onClickFunction
import react.RProps
import styled.css
import styled.styledDiv
import utils.EventHandler
import utils.FunctionalComponent

interface BackdropProps : RProps {

	var show: Boolean
	var clicked: EventHandler

}

val backdrop by FunctionalComponent<BackdropProps> { props ->
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
