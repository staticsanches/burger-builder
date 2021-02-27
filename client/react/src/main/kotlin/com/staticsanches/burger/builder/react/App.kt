package com.staticsanches.burger.builder.react

import com.staticsanches.burger.builder.react.components.layout.layout
import com.staticsanches.burger.builder.react.containers.burgerBuilder
import com.staticsanches.burger.builder.react.containers.checkout
import com.staticsanches.burger.builder.react.containers.orders
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import com.staticsanches.burger.builder.react.utils.route
import react.RProps
import react.buildElement
import react.dom.h1
import react.router.dom.switch


val app by FunctionalComponentDelegate<RProps> {
	layout {
		switch {
			route(path = "/checkout", component = checkout)
			route(path = "/orders", component = orders)
			route(path = "/", exact = true, component = burgerBuilder)
			route(path = "/") {
				buildElement {
					h1 { +"404: Page not found" }
				}
			}
		}
	}
}
