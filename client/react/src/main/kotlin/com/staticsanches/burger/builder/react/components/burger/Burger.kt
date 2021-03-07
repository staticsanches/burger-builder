package com.staticsanches.burger.builder.react.components.burger

import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinx.css.*
import react.RBuilder
import react.RProps
import react.dom.p
import styled.css
import styled.styledDiv
import kotlin.reflect.KProperty0

interface BurgerProps : RProps {

	var ingredients: BurgerIngredients

}

val burger by FunctionalComponentDelegate<BurgerProps> { props ->
	styledDiv {
		css {
			width = 100.pct
			margin(LinearDimension.auto)
			height = 250.px
			overflow = Overflow.auto
			textAlign = TextAlign.center
			fontWeight = FontWeight.bold
			fontSize = 1.2.rem

			media("(min-width: 500px) and (min-height: 400px)") {
				width = 350.px
				height = 300.px
			}

			media("(min-width: 500px) and (min-height: 401px)") {
				width = 450.px
				height = 400.px
			}

			media("(min-width: 1000px) and (min-height: 700px)") {
				width = 700.px
				height = 600.px
			}
		}
		ingredient {
			attrs.type = IngredientType.TOP_BUN
		}
		if (props.ingredients.total == 0) {
			p { +"Please, start adding ingredients!" }
		} else {
			toIngredients(props.ingredients::salad)
			toIngredients(props.ingredients::bacon)
			toIngredients(props.ingredients::cheese)
			toIngredients(props.ingredients::meat)
		}
		ingredient {
			attrs.type = IngredientType.BOTTOM_BUN
		}
	}
}

private fun RBuilder.toIngredients(property: KProperty0<Int>) {
	val type = IngredientType.valueOf(property.name.toUpperCase())
	List(property.get()) { type }
		.renderEachIndexed { index, _ ->
			ingredient {
				attrs {
					key = "${type}_$index"
					this.type = type
				}
			}
		}
}
