package com.staticsanches.burger.builder.react.store.slices

import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredientType
import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredients
import redux.RAction

object BurgerSlice {

	val initialState = State()

	data class State(
		val ingredients: BurgerIngredients = BurgerIngredients(),
		val price: Double = 4.0
	)

	class AddIngredient(val ingredientType: BurgerIngredientType) : RAction
	class RemoveIngredient(val ingredientType: BurgerIngredientType) : RAction

	fun reducer(state: State = initialState, action: RAction): State {
		return when (action) {
			is AddIngredient -> state.copy(
				ingredients = state.ingredients.copy(
					action.ingredientType,
					state.ingredients[action.ingredientType] + 1
				),
				price = state.price + action.ingredientType.price
			)
			is RemoveIngredient -> state.copy(
				ingredients = state.ingredients.copy(
					action.ingredientType,
					state.ingredients[action.ingredientType] - 1
				),
				price = state.price - action.ingredientType.price
			)
			else -> state
		}
	}

}

private val BurgerIngredientType.price: Double
	get() = when (this) {
		BurgerIngredientType.SALAD -> 0.5
		BurgerIngredientType.BACON -> 0.7
		BurgerIngredientType.CHEESE -> 0.4
		BurgerIngredientType.MEAT -> 1.3
		else -> error("Invalid ingredient: $label")
	}
