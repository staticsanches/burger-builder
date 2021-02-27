package com.staticsanches.burger.builder.react.components.layout

import com.staticsanches.burger.builder.react.components.navigation.sideDrawer
import com.staticsanches.burger.builder.react.components.navigation.toolbar
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import external.styled.styledMain
import kotlinx.css.marginTop
import kotlinx.css.px
import react.RProps
import react.useState
import styled.css


val layout by FunctionalComponentDelegate<RProps> { props ->
	val (showSideDrawer, setShowSideDrawer) = useState(false)

	val sideDrawerClosedHandler: EventHandler = { setShowSideDrawer(false) }
	val sideDrawerToggleHandler: EventHandler = { setShowSideDrawer(!showSideDrawer) }

	toolbar {
		attrs.drawerToggleClicked = sideDrawerToggleHandler
	}
	sideDrawer {
		attrs {
			open = showSideDrawer
			closed = sideDrawerClosedHandler
		}
	}
	styledMain {
		css {
			marginTop = 72.px
		}
		props.children()
	}
}
