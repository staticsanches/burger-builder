package components.burger

import components.ui.ButtonType
import components.ui.button
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
import utils.EventHandler
import utils.FunctionalComponent
import utils.toFixed

interface OrderSummaryProps : RProps {

	var ingredients: BurgerIngredients
	var price: Number

	var purchaseCancelled: EventHandler
	var purchaseContinued: EventHandler

}

val orderSummary by FunctionalComponent<OrderSummaryProps> { props ->
	h3 { +"Your Order" }
	p { +"A delicious burger with the following ingredients:" }
	ul {
		IngredientType.configurableValues
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
