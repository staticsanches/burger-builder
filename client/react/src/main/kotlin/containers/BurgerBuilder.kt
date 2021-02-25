package containers

import axios.axiosOrder
import com.staticsanches.burger.builder.react.hoc.withErrorBoundary
import com.staticsanches.burger.builder.react.utils.hooks.useAsyncError
import com.staticsanches.burger.builder.shared.js.json.convertFromJson
import components.burger.*
import components.ui.modal
import components.ui.spinner
import react.RProps
import react.dom.p
import react.router.dom.RouteResultProps
import react.useEffect
import react.useState
import utils.EventHandler
import utils.FunctionalComponent

interface BurgerBuilderProps : RouteResultProps<RProps>

val burgerBuilder by FunctionalComponent<BurgerBuilderProps>(
	withErrorBoundary { p { +"Ingredients can't be loaded!" } }
) { props ->
	val (ingredients, setIngredients) = useState<BurgerIngredients?>(null)
	val (totalPrice, setTotalPrice) = useState(initialPrice)
	val (purchasing, setPurchasing) = useState(false)
	val throwError = useAsyncError()

	useEffect(emptyList()) {
		axiosOrder.get<Any>("/ingredients.json")
			.then {
				val newIngredients: BurgerIngredients = it.data.convertFromJson()
				val newPrice = IngredientType.configurableValues
					.fold(initialPrice) { acc, type ->
						acc + newIngredients[type] * type.price
					}
				setIngredients(newIngredients)
				setTotalPrice(newPrice)
			}
			.catch {
				throwError(Error("Testing error"))
			}
	}

	val removeIngredientHandler = remove@{ type: IngredientType ->
		val oldCount = ingredients!![type]
		if (oldCount == 0) {
			return@remove // nothing to remove
		}
		setIngredients(ingredients.copy(type, oldCount - 1))
		setTotalPrice(totalPrice - type.price)
	}
	val addIngredientHandler = { type: IngredientType ->
		setIngredients(ingredients!!.copy(type, ingredients[type] + 1))
		setTotalPrice(totalPrice + type.price)
	}
	val disabled = disabled@{ type: IngredientType ->
		return@disabled ingredients!![type] == 0
	}
	val purchaseHandler: EventHandler = { setPurchasing(true) }
	val purchaseCancelHandler: EventHandler = { setPurchasing(false) }
	val purchaseContinueHandler: EventHandler = {
		val queryParams = IngredientType.configurableValues
			.joinToString("&") {
				"${it.name}=${ingredients!![it]}"
			} + "&price=$totalPrice"
		props.history.push("/checkout?$queryParams")
	}

	if (ingredients == null) {
		spinner {}
	} else {
		burger {
			attrs.ingredients = ingredients
		}
		buildControls {
			attrs {
				price = totalPrice
				purchasable = ingredients.total > 0
				ordered = purchaseHandler
				this.disabled = disabled
				lessHandler = removeIngredientHandler
				moreHandler = addIngredientHandler
			}
		}
	}
	modal {
		attrs {
			show = purchasing
			modalClosed = purchaseCancelHandler
		}
		if (ingredients != null) {
			orderSummary {
				attrs {
					this.ingredients = ingredients
					price = totalPrice
					purchaseCancelled = purchaseCancelHandler
					purchaseContinued = purchaseContinueHandler
				}
			}
		}
	}
}

private const val initialPrice = 4.0

private val IngredientType.price: Double
	get() = when (this) {
		IngredientType.SALAD -> 0.5
		IngredientType.BACON -> 0.7
		IngredientType.CHEESE -> 0.4
		IngredientType.MEAT -> 1.3
		else -> error("Invalid ingredient: $label")
	}
