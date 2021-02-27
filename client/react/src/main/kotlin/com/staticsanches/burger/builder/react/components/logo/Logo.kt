package com.staticsanches.burger.builder.react.components.logo

import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.css.borderRadius
import kotlinx.css.boxSizing
import kotlinx.css.height
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.px
import react.RProps
import react.dom.img
import styled.css
import styled.styledDiv


val logo by FunctionalComponentDelegate<RProps> {
	styledDiv {
		css {
			backgroundColor = Color.white
			padding(8.px)
			height = 100.pct
			boxSizing = BoxSizing.borderBox
			borderRadius = 5.px

			kotlinx.css.img {
				height = 100.pct
			}
		}
		img {
			attrs {
				src = "/assets/images/burger-logo.png"
				alt = "BurgerBuilder"
			}
		}
	}
}
