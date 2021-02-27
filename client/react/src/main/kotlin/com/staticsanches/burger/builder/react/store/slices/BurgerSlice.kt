package com.staticsanches.burger.builder.react.store.slices

import com.staticsanches.burger.builder.react.components.burger.BurgerIngredients
import com.staticsanches.burger.builder.react.components.burger.IngredientType
import redux.RAction

object BurgerSlice {

	val initialState = State()

	data class State(
		val ingredients: BurgerIngredients = BurgerIngredients(),
		val price: Double = 4.0
	)

	class AddIngredient(val ingredientType: IngredientType) : RAction
	class RemoveIngredient(val ingredientType: IngredientType) : RAction

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

private val IngredientType.price: Double
	get() = when (this) {
		IngredientType.SALAD -> 0.5
		IngredientType.BACON -> 0.7
		IngredientType.CHEESE -> 0.4
		IngredientType.MEAT -> 1.3
		else -> error("Invalid ingredient: $label")
	}
