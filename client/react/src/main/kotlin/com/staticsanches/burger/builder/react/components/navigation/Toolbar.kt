package com.staticsanches.burger.builder.react.components.navigation

import com.staticsanches.burger.builder.react.components.logo.logo
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.*
import react.RProps
import styled.css
import styled.styledDiv
import styled.styledHeader
import styled.styledNav

interface ToolbarProps : RProps {

	var drawerToggleClicked: EventHandler

}

val toolbar by FunctionalComponentDelegate<ToolbarProps> { props ->
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
