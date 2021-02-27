package com.staticsanches.burger.builder.react.components.navigation

import com.staticsanches.burger.builder.react.components.logo.logo
import com.staticsanches.burger.builder.react.components.ui.backdrop
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.Position
import kotlinx.css.backgroundColor
import kotlinx.css.boxSizing
import kotlinx.css.display
import kotlinx.css.height
import kotlinx.css.left
import kotlinx.css.marginBottom
import kotlinx.css.maxWidth
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.Timing
import kotlinx.css.properties.s
import kotlinx.css.properties.transform
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateX
import kotlinx.css.px
import kotlinx.css.top
import kotlinx.css.width
import kotlinx.css.zIndex
import react.RProps
import react.dom.nav
import styled.css
import styled.styledDiv

interface SideDrawerProps : RProps {

	var open: Boolean
	var closed: EventHandler

}

val sideDrawer by FunctionalComponentDelegate<SideDrawerProps> { props ->
	backdrop {
		attrs {
			show = props.open
			clicked = props.closed
		}
	}
	styledDiv {
		css {
			position = Position.fixed
			width = 280.px
			maxWidth = 70.pct
			height = 100.pct
			left = 0.px
			top = 0.px
			zIndex = 200
			backgroundColor = Color.white
			padding(32.px, 16.px)
			boxSizing = BoxSizing.borderBox
			transition("transform", 0.3.s, Timing.easeOut)

			media("(min-width: 500px)") {
				display = Display.none
			}

			transform {
				translateX(if (props.open) 0.px else (-100).pct)
			}
		}

		styledDiv {
			css {
				height = 11.pct
				marginBottom = 32.px
			}
			logo {}
		}
		nav {
			navigationItems {}
		}
	}
}
