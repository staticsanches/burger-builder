package com.staticsanches.burger.builder.shared.model.burger

import kotlinx.collections.immutable.toImmutableList

enum class BurgerIngredientType(val label: String, private val configurable: Boolean = true) {

	TOP_BUN("Top Bun", false),
	SALAD("Salad"),
	BACON("Bacon"),
	CHEESE("Cheese"),
	MEAT("Meat"),
	BOTTOM_BUN("Bottom Bun", false);

	companion object {

		val configurableValues = values()
			.filter(BurgerIngredientType::configurable)
			.toImmutableList()

	}

}
