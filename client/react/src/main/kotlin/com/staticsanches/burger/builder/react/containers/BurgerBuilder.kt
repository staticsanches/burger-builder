package com.staticsanches.burger.builder.react.containers

import com.staticsanches.burger.builder.react.components.burger.*
import com.staticsanches.burger.builder.react.components.ui.modal
import com.staticsanches.burger.builder.react.hoc.plus
import com.staticsanches.burger.builder.react.hoc.rConnect
import com.staticsanches.burger.builder.react.hoc.withErrorBoundary
import com.staticsanches.burger.builder.react.hoc.wrappedBy
import com.staticsanches.burger.builder.react.store.slices.BurgerSlice.AddIngredient
import com.staticsanches.burger.builder.react.store.slices.BurgerSlice.RemoveIngredient
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredientType
import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredients
import react.*
import react.dom.p
import react.router.dom.RouteResultProps

val burgerBuilder: RClass<RProps> =
	BurgerBuilder::class wrappedBy rConnect<BurgerBuilderProps, RProps>(
		{ state ->
			ingredients = state.burgerState.ingredients
			price = state.burgerState.price
		},
		{ dispatch ->
			onIngredientAdded = { ingredientType -> dispatch(AddIngredient(ingredientType)) }
			onIngredientRemoved = { ingredientType -> dispatch(RemoveIngredient(ingredientType)) }
		}
	) + withErrorBoundary {
		p { +"Ingredients can't be loaded!" }
	}

private interface BurgerBuilderProps : RouteResultProps<RProps> {

	var ingredients: BurgerIngredients
	var price: Double

	var onIngredientAdded: (BurgerIngredientType) -> Unit
	var onIngredientRemoved: (BurgerIngredientType) -> Unit

}

private interface BurgerBuilderState : RState {

	var purchasing: Boolean

}

private class BurgerBuilder(initialProps: BurgerBuilderProps) :
	RComponent<BurgerBuilderProps, BurgerBuilderState>(initialProps) {

	override fun BurgerBuilderState.init(props: BurgerBuilderProps) {
		purchasing = false
	}

	val disabled = { type: BurgerIngredientType -> props.ingredients[type] == 0 }

	val purchaseHandler: EventHandler = {
		setState { purchasing = true }
	}

	val purchaseCancelHandler: EventHandler = {
		setState { purchasing = false }
	}

	val purchaseContinueHandler: EventHandler = {
		props.history.push("/checkout")
	}

	override fun RBuilder.render() {
		val price = props.price
		val ingredients = props.ingredients

		burger {
			attrs.ingredients = ingredients
		}
		buildControls {
			attrs {
				this.price = price
				purchasable = ingredients.total > 0
				ordered = purchaseHandler
				disabled = this@BurgerBuilder.disabled
				lessHandler = props.onIngredientRemoved
				moreHandler = props.onIngredientAdded
			}
		}
		modal {
			attrs {
				show = state.purchasing
				modalClosed = purchaseCancelHandler
			}
			orderSummary {
				attrs {
					this.ingredients = ingredients
					this.price = price
					purchaseCancelled = purchaseCancelHandler
					purchaseContinued = purchaseContinueHandler
				}
			}
		}
	}

}
