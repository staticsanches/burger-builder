package com.staticsanches.burger.builder.react.components.burger

import com.staticsanches.burger.builder.react.components.ui.ButtonType
import com.staticsanches.burger.builder.react.components.ui.button
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import com.staticsanches.burger.builder.shared.js.utils.toFixed
import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredientType
import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredients
import kotlinx.css.TextTransform
import kotlinx.css.textTransform
import react.RProps
import react.dom.h3
import react.dom.li
import react.dom.p
import react.dom.strong
import react.dom.ul
import styled.css
import styled.styledSpan

interface OrderSummaryProps : RProps {

	var ingredients: BurgerIngredients
	var price: Number

	var purchaseCancelled: EventHandler
	var purchaseContinued: EventHandler

}

val orderSummary by FunctionalComponentDelegate<OrderSummaryProps> { props ->
	h3 { +"Your Order" }
	p { +"A delicious burger with the following ingredients:" }
	ul {
		BurgerIngredientType.configurableValues
			.renderEach {
				li {
					key = it.name
					styledSpan {
						css { textTransform = TextTransform.capitalize }
						+it.label
					}
					+": ${props.ingredients[it]}"
				}
			}
	}
	p { strong { +"Total Price: \$${props.price.toFixed(2)}" } }
	p { +"Continue to Checkout?" }
	button {
		attrs {
			buttonType = ButtonType.DANGER
			clicked = props.purchaseCancelled
		}
		+"CANCEL"
	}
	button {
		attrs {
			buttonType = ButtonType.SUCCESS
			clicked = props.purchaseContinued
		}
		+"CONTINUE"
	}
}
