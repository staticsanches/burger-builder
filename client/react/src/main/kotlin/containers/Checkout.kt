package containers

import components.burger.BurgerIngredients
import components.burger.IngredientType
import components.order.checkoutSummary
import kotlinext.js.Object
import kotlinext.js.jsObject
import org.w3c.dom.url.URLSearchParams
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.router.dom.RouteResultProps
import react.router.dom.route
import utils.EventHandler
import utils.rElement
import utils.setState

interface CheckoutProps : RouteResultProps<RProps>
interface CheckoutState : RState {

	var ingredients: BurgerIngredients
	var totalPrice: Number

}

class Checkout(initialProps: CheckoutProps) : RComponent<CheckoutProps, CheckoutState>(initialProps) {

	override fun CheckoutState.init(props: CheckoutProps) {
		this.ingredients = BurgerIngredients()
	}

	override fun componentDidMount() {
		val query = URLSearchParams(this.props.location.search)
		val ingredients = BurgerIngredients(
			salad = query[IngredientType.SALAD],
			bacon = query[IngredientType.BACON],
			cheese = query[IngredientType.CHEESE],
			meat = query[IngredientType.MEAT]
		)
		val totalPrice = query.get("price")?.toDouble() ?: 0
		this.setState {
			this.ingredients = ingredients
			this.totalPrice = totalPrice
		}
	}

	private val checkoutCancelledHandler: EventHandler = {
		this.props.history.goBack()
	}
	private val checkoutContinuedHandler: EventHandler = {
		this.props.history.replace("/checkout/contact-data")
	}

	override fun RBuilder.render() {
		div {
			checkoutSummary {
				attrs {
					checkoutCancelled = checkoutCancelledHandler
					checkoutContinued = checkoutContinuedHandler
					ingredients = state.ingredients
				}
			}
			route<RProps>(props.match.path + "/contact-data") { initialProps ->
				val props: ContactDataProps = Object.assign(jsObject(), initialProps)
				props.ingredients = state.ingredients
				props.price = state.totalPrice
				contactData.rElement(props)
			}
		}
	}

}

private operator fun URLSearchParams.get(type: IngredientType): Int =
	this.get(type.name)?.toInt() ?: 0
