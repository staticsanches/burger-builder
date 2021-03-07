package com.staticsanches.burger.builder.shared.model.burger

import kotlinx.serialization.Serializable

@Serializable
data class BurgerIngredients(
	val salad: Int = 0,
	val bacon: Int = 0,
	val cheese: Int = 0,
	val meat: Int = 0
) {
	init {
		require(salad >= 0) { "salad cannot be negative" }
		require(bacon >= 0) { "bacon cannot be negative" }
		require(cheese >= 0) { "cheese cannot be negative" }
		require(meat >= 0) { "meat cannot be negative" }
	}

	val total: Int
		get() = salad + bacon + cheese + meat

	operator fun get(type: BurgerIngredientType): Int =
		when(type) {
			BurgerIngredientType.SALAD -> salad
			BurgerIngredientType.BACON -> bacon
			BurgerIngredientType.CHEESE -> cheese
			BurgerIngredientType.MEAT -> meat
			else -> error("Invalid ingredient: ${type.label}")
		}

	fun copy(type: BurgerIngredientType, newValue: Int): BurgerIngredients =
		when(type) {
			BurgerIngredientType.SALAD -> copy(salad = newValue)
			BurgerIngredientType.BACON -> copy(bacon = newValue)
			BurgerIngredientType.CHEESE -> copy(cheese = newValue)
			BurgerIngredientType.MEAT -> copy(meat = newValue)
			else -> error("Invalid ingredient: ${type.label}")
		}

}
