package components.navigation

import components.logo.logo
import kotlinx.css.Align
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.Position
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.boxSizing
import kotlinx.css.display
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.left
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.px
import kotlinx.css.top
import kotlinx.css.width
import kotlinx.css.zIndex
import react.RProps
import styled.css
import styled.styledDiv
import styled.styledHeader
import styled.styledNav
import utils.EventHandler
import utils.FunctionalComponent

interface ToolbarProps : RProps {

	var drawerToggleClicked: EventHandler

}

val toolbar by FunctionalComponent<ToolbarProps> { props ->
	styledHeader {
		css {
			height = 56.px
			width = 100.pct
			position = Position.fixed
			top = 0.px
			left = 0.px
			backgroundColor = Color("#703B09")
			display = Display.flex
			justifyContent = JustifyContent.spaceBetween
			alignItems = Align.center
			padding(0.px, 20.px)
			boxSizing = BoxSizing.borderBox
			zIndex = 90

			kotlinx.css.nav {
				height = 100.pct
			}
		}
		drawerToggle {
			attrs.clicked = props.drawerToggleClicked
		}
		styledDiv {
			css {
				height = 80.pct
			}
			logo {}
		}
		styledNav {
			css {
				media("(max-width: 499px)") {
					display = Display.none
				}
			}

			navigationItems {}
		}
	}
}
