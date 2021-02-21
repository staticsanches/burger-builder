package components.layout

import components.navigation.sideDrawer
import components.navigation.toolbar
import external.styled.styledMain
import kotlinx.css.marginTop
import kotlinx.css.px
import react.RProps
import react.useState
import styled.css
import utils.EventHandler
import utils.FunctionalComponent


val layout by FunctionalComponent<RProps> { props ->
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
