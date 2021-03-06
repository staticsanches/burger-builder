package com.staticsanches.burger.builder.react.components.navigation

import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.RProps
import react.dom.div
import styled.css
import styled.styledDiv

interface DrawerToggleProps : RProps {

	var clicked: EventHandler

}

val drawerToggle by FunctionalComponentDelegate<DrawerToggleProps> { props ->
	styledDiv {
		css {
			width = 40.px
			height = 100.pct
			display = Display.flex
			flexDirection = FlexDirection.column
			justifyContent = JustifyContent.spaceAround
			alignItems = Align.center
			padding(10.px, 0.px)
			boxSizing = BoxSizing.borderBox
			cursor = Cursor.pointer

			kotlinx.css.div {
				width = 90.pct
				height = 3.px
				backgroundColor = Color.white
			}

			media("(min-width: 500px)") {
				display = Display.none
			}
		}
		attrs.onClickFunction = props.clicked
		div { }
		div { }
		div { }
	}
}
