package containers

import axios.axiosOrder
import com.staticsanches.burger.builder.shared.js.json.convertFromJson
import components.burger.BurgerIngredients
import components.order.order
import components.ui.spinner
import hoc.withErrorHandler
import kotlinext.js.Object
import kotlinx.serialization.Serializable
import react.RBuilder
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.key
import react.rClass
import utils.setState

val orders: RClass<RProps> =
	withErrorHandler<RProps>(axiosOrder)(Orders::class.rClass)

@Serializable
data class RemoteOrder(
	var id: String = "",
	val ingredients: BurgerIngredients,
	val price: Double
)

private interface OrdersState : RState {

	var orders: List<RemoteOrder>
	var loading: Boolean

}

private class Orders(initialProps: RProps) : RComponent<RProps, OrdersState>(initialProps) {

	override fun OrdersState.init(props: RProps) {
		this.orders = emptyList()
		this.loading = true
	}

	override fun componentDidMount() {
		axiosOrder.get<Any>("/orders.json")
			.then {
				val fetchedOrders = Object.keys(it.data).map { key ->
					val order = (it.data.asDynamic()[key] as Any).convertFromJson<RemoteOrder>()
					order.id = key
					order
				}
				setState {
					orders = fetchedOrders
					loading = false
				}
			}
			.catch {
				setState {
					loading = false
				}
			}
	}

	override fun RBuilder.render() {
		div {
			if (state.loading) {
				spinner {}
			} else {
				state.orders.renderEach { o ->
					order {
						attrs {
							key = o.id
							ingredients = o.ingredients
							price = o.price
						}
					}
				}
			}
		}
	}

}
