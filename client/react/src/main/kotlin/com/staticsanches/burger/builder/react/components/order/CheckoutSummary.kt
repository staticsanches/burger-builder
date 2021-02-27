package com.staticsanches.burger.builder.react.components.order

import com.staticsanches.burger.builder.react.components.burger.BurgerIngredients
import com.staticsanches.burger.builder.react.components.burger.burger
import com.staticsanches.burger.builder.react.components.ui.ButtonType
import com.staticsanches.burger.builder.react.components.ui.button
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.LinearDimension
import kotlinx.css.TextAlign
import kotlinx.css.margin
import kotlinx.css.pct
import kotlinx.css.px
import kotlinx.css.textAlign
import kotlinx.css.width
import react.RProps
import react.dom.h1
import styled.css
import styled.styledDiv

interface CheckoutSummaryProps : RProps {

	var ingredients: BurgerIngredients

	var checkoutCancelled: EventHandler
	var checkoutContinued: EventHandler

}

val checkoutSummary by FunctionalComponentDelegate<CheckoutSummaryProps> { props ->
	styledDiv {
		css {
			textAlign = TextAlign.center
			width = 80.pct
			margin(LinearDimension.auto)

			media("(min-width: 600px)") {
				width = 500.px
			}
		}

		h1 { +"We hope it tastes well!" }
		styledDiv {
			css {
				width = 100.pct
				margin(LinearDimension.auto)
			}
			burger {
				attrs.ingredients = props.ingredients
			}
		}
		button {
			attrs {
				clicked = props.checkoutCancelled
				buttonType = ButtonType.DANGER
			}
			+"CANCEL"
		}
		button {
			attrs {
				clicked = props.checkoutContinued
				buttonType = ButtonType.SUCCESS
			}
			+"CONTINUE"
		}
	}
}
