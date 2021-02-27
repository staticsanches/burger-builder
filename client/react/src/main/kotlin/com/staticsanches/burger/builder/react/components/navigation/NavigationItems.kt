package com.staticsanches.burger.builder.react.components.navigation

import com.staticsanches.burger.builder.react.hoc.defaultProps
import com.staticsanches.burger.builder.react.hoc.wrappedBy
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.Align
import kotlinx.css.BorderStyle
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.LinearDimension
import kotlinx.css.ListStyleType
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.borderBottomColor
import kotlinx.css.borderBottomStyle
import kotlinx.css.borderBottomWidth
import kotlinx.css.boxSizing
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.height
import kotlinx.css.listStyleType
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.TextDecoration
import kotlinx.css.px
import kotlinx.css.textDecoration
import kotlinx.css.width
import react.RProps
import react.router.dom.navLink
import styled.StyleSheet
import styled.css
import styled.styledLi
import styled.styledUl

val navigationItems by FunctionalComponentDelegate<RProps> {
	styledUl {
		css { +NavigationItemsStyles.navigationItems }

		navigationItem {
			attrs {
				link = "/"
				exact = true
			}
			+"Burger Builder"
		}
		navigationItem {
			attrs.link = "/orders"
			+"Orders"
		}
	}
}

private interface NavigationItemProps : RProps {

	var link: String
	var exact: Boolean

}

private val navigationItem by FunctionalComponentDelegate<NavigationItemProps> { props ->
	styledLi {
		css { +NavigationItemsStyles.navigationItem }
		navLink<RProps>(
			to = props.link,
			exact = props.exact
		) {
			props.children()
		}
	}
} wrappedBy defaultProps { exact = false }

private object NavigationItemsStyles : StyleSheet("NavigationItems") {

	val navigationItems by css {
		margin(0.px)
		padding(0.px)
		listStyleType = ListStyleType.none
		display = Display.flex
		flexDirection = FlexDirection.column
		alignItems = Align.center
		height = 100.pct

		media("(min-width: 500px)") {
			flexDirection = FlexDirection.row
		}
	}

	val navigationItem by css {
		margin(10.px, 0.px)
		boxSizing = BoxSizing.borderBox
		display = Display.block
		width = 100.pct

		val active by css {
			color = Color("#40A4C8")

			media("(min-width: 500px)") {
				backgroundColor = Color("#8F5C2C")
				borderBottomWidth = 4.px
				borderBottomStyle = BorderStyle.solid
				borderBottomColor = Color("#40A4C8")
				color = Color.white
			}
		}

		descendants("a") {
			color = Color("#8F5C2C")
			textDecoration = TextDecoration.none
			width = 100.pct
			boxSizing = BoxSizing.borderBox
			display = Display.block

			hover(active)
			active(active)
			"&.active" {
				+active
			}

			media("(min-width: 500px)") {
				color = Color.white
				textDecoration = TextDecoration.none
				height = 100.pct
				padding(16.px, 10.px)
				borderBottomWidth = 4.px
				borderBottomStyle = BorderStyle.solid
				borderBottomColor = Color.transparent
			}
		}

		media("(min-width: 500px)") {
			margin(0.px)
			display = Display.flex
			height = 100.pct
			width = LinearDimension.auto
			alignItems = Align.center
		}
	}

}
