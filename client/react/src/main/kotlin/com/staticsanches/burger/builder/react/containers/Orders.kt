package com.staticsanches.burger.builder.react.containers

import com.staticsanches.burger.builder.react.axios.axiosOrder
import com.staticsanches.burger.builder.react.components.order.order
import com.staticsanches.burger.builder.react.components.ui.spinner
import com.staticsanches.burger.builder.react.hoc.withErrorBoundary
import com.staticsanches.burger.builder.react.hoc.wrappedBy
import com.staticsanches.burger.builder.shared.js.json.convertFromJson
import com.staticsanches.burger.builder.shared.model.burger.BurgerIngredients
import kotlinext.js.Object
import kotlinx.serialization.Serializable
import react.*
import react.dom.div
import react.dom.p

val orders: RClass<RProps> =
	Orders::class wrappedBy withErrorBoundary { p { +"Unable to load orders!" } }

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
