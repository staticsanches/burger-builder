package com.staticsanches.burger.builder.react

import com.staticsanches.burger.builder.react.store.store
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.body
import kotlinx.css.fontFamily
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.px
import react.dom.render
import react.redux.provider
import react.router.dom.browserRouter
import styled.injectGlobal

fun main() {

	injectGlobal {
		body {
			margin(0.px)
			padding(0.px)
			fontFamily = "\"Open Sans\", sans-serif"
		}
	}

	window.onload = {
		render(document.getElementById("root")) {
			provider(store = store) {
				browserRouter {
					app {}
				}
			}
		}
	}

}
