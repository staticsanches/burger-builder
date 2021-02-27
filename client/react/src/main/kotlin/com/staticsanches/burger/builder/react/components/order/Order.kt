package com.staticsanches.burger.builder.react.components.order

import com.staticsanches.burger.builder.react.components.burger.BurgerIngredients
import com.staticsanches.burger.builder.react.components.burger.IngredientType
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import com.staticsanches.burger.builder.shared.js.utils.toFixed
import kotlinx.css.BorderStyle
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.LinearDimension
import kotlinx.css.boxSizing
import kotlinx.css.display
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import kotlinx.css.px
import kotlinx.css.width
import react.RProps
import react.dom.key
import react.dom.p
import react.dom.strong
import styled.css
import styled.styledDiv
import styled.styledSpan

interface OrderProps : RProps {

	var ingredients: BurgerIngredients
	var price: Number

}

val order by FunctionalComponentDelegate<OrderProps> { props ->
	styledDiv {
		css {
			width = 80.pct
			border(1.px, BorderStyle.solid, Color("#eee"))
			boxShadow(offsetX = 0.px, offsetY = 2.px, blurRadius = 3.px, color = Color("#ccc"))
			padding(10.px)
			margin(10.px, LinearDimension.auto)
			boxSizing = BoxSizing.borderBox
		}
		p {
			+"Ingredients: "
			IngredientType.configurableValues.renderEach { type ->
				styledSpan {
					css {
						display = Display.inlineBlock
						margin(0.px, 8.px)
						border(1.px, BorderStyle.solid, Color("#ccc"))
						padding(5.px)
					}
					attrs.key = type.name

					+"${type.label} ${props.ingredients[type]}"
				}
			}
		}
		p {
			+"Price: "
			strong { +"\$ ${props.price.toFixed(2)}" }
		}
	}
}
