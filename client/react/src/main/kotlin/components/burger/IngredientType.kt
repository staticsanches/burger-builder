package components.burger

enum class IngredientType(val label: String, private val configurable: Boolean = true) {

	TOP_BUN("Top Bun", false),
	SALAD("Salad"),
	BACON("Bacon"),
	CHEESE("Cheese"),
	MEAT("Meat"),
	BOTTOM_BUN("Bottom Bun", false);

	companion object {

		val configurableValues = values()
			.filter(IngredientType::configurable)
			.toList()

	}

}
