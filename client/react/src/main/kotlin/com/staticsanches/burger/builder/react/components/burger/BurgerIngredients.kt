package com.staticsanches.burger.builder.react.components.burger

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

	operator fun get(type: IngredientType): Int =
		when (type) {
			IngredientType.SALAD -> salad
			IngredientType.BACON -> bacon
			IngredientType.CHEESE -> cheese
			IngredientType.MEAT -> meat
			else -> error("Invalid ingredient: ${type.label}")
		}

	fun copy(type: IngredientType, newValue: Int): BurgerIngredients =
		when (type) {
			IngredientType.SALAD -> copy(salad = newValue)
			IngredientType.BACON -> copy(bacon = newValue)
			IngredientType.CHEESE -> copy(cheese = newValue)
			IngredientType.MEAT -> copy(meat = newValue)
			else -> error("Invalid ingredient: ${type.label}")
		}

}
