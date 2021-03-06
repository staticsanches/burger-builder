package com.staticsanches.burger.builder.react.containers

import com.staticsanches.burger.builder.react.components.order.checkoutSummary
import com.staticsanches.burger.builder.react.hoc.rConnect
import com.staticsanches.burger.builder.react.hoc.wrappedBy
import com.staticsanches.burger.builder.react.store.AppState
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import com.staticsanches.burger.builder.react.utils.route
import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredients
import react.RClass
import react.RProps
import react.dom.div
import react.router.dom.RouteResultProps

val checkout: RClass<RProps> by FunctionalComponentDelegate<CheckoutProps> { props ->
	val checkoutCancelledHandler: EventHandler = {
		props.history.goBack()
	}
	val checkoutContinuedHandler: EventHandler = {
		props.history.replace("/checkout/contact-data")
	}
	div {
		checkoutSummary {
			attrs {
				checkoutCancelled = checkoutCancelledHandler
				checkoutContinued = checkoutContinuedHandler
				ingredients = props.ingredients
			}
		}
		route(path = props.match.path + "/contact-data", component = contactData)
	}
} wrappedBy
		rConnect({ state: AppState ->
			ingredients = state.burgerState.ingredients
		})

private interface CheckoutProps : RouteResultProps<RProps> {

	var ingredients: BurgerIngredients

}
