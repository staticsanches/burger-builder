import components.layout.layout
import containers.burgerBuilder
import containers.checkout
import containers.orders
import react.RProps
import react.buildElement
import react.dom.h1
import react.router.dom.switch
import utils.FunctionalComponent
import utils.route


val app by FunctionalComponent<RProps> {
	layout {
		switch {
			route(path = "/checkout", component = checkout)
			route(path = "/orders", component = orders)
			route(path = "/", exact = true, component = burgerBuilder)
			route(path = "/") { _ ->
				buildElement {
					h1 { +"404: Page not found" }
				}
			}
		}
	}
}
