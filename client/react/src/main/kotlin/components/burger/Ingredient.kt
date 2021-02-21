package components.burger

import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.Position
import kotlinx.css.background
import kotlinx.css.backgroundColor
import kotlinx.css.borderBottomLeftRadius
import kotlinx.css.borderBottomRightRadius
import kotlinx.css.borderRadius
import kotlinx.css.borderTopLeftRadius
import kotlinx.css.borderTopRightRadius
import kotlinx.css.content
import kotlinx.css.height
import kotlinx.css.left
import kotlinx.css.margin
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.boxShadowInset
import kotlinx.css.properties.deg
import kotlinx.css.properties.rotate
import kotlinx.css.properties.transform
import kotlinx.css.px
import kotlinx.css.quoted
import kotlinx.css.top
import kotlinx.css.width
import react.RProps
import styled.StyleSheet
import styled.css
import styled.styledDiv
import utils.FunctionalComponent

interface IngredientProps : RProps {

	var type: IngredientType

}

val ingredient by FunctionalComponent<IngredientProps> { props ->
	when (props.type) {
		IngredientType.TOP_BUN -> {
			styledDiv {
				css {
					+IngredientStyles.topBun
				}
				styledDiv {
					css {
						+IngredientStyles.seeds1
					}
				}
				styledDiv {
					css {
						+IngredientStyles.seeds2
					}
				}
			}
		}
		IngredientType.SALAD -> {
			styledDiv {
				css {
					+IngredientStyles.salad
				}
			}
		}
		IngredientType.BACON -> {
			styledDiv {
				css {
					+IngredientStyles.bacon
				}
			}
		}
		IngredientType.CHEESE -> {
			styledDiv {
				css {
					+IngredientStyles.cheese
				}
			}
		}
		IngredientType.MEAT -> {
			styledDiv {
				css {
					+IngredientStyles.meat
				}
			}
		}
		IngredientType.BOTTOM_BUN -> {
			styledDiv {
				css {
					+IngredientStyles.bottomBun
				}
			}
		}
	}
}

private object IngredientStyles : StyleSheet("IngredientStyles") {

	val topBun by css {
		height = 20.pct
		width = 80.pct
		background = "linear-gradient(#bc581e, #e27b36)"
		borderTopLeftRadius = 50.pct
		borderTopRightRadius = 50.pct
		boxShadowInset(offsetX = (-15).px, offsetY = 0.px, color = Color("#c15711"))
		margin(2.pct, LinearDimension.auto)
		position = Position.relative
	}

	val seeds1 by css {
		width = 10.pct
		height = 15.pct
		position = Position.absolute
		backgroundColor = Color.white
		left = 30.pct
		top = 50.pct
		borderRadius = 40.pct
		transform {
			rotate((-20).deg)
		}
		boxShadowInset(offsetX = (-2).px, offsetY = (-3).px, color = Color("#c9c9c9"))

		after {
			content = "".quoted
			width = 100.pct
			height = 100.pct
			position = Position.absolute
			backgroundColor = Color.white
			left = (-170).pct
			top = (-260).pct
			borderRadius = 40.pct
			transform {
				rotate(60.deg)
			}
			boxShadowInset(offsetX = (-1).px, offsetY = 2.px, color = Color("#c9c9c9"))
		}

		before {
			content = "".quoted
			width = 100.pct
			height = 100.pct
			position = Position.absolute
			backgroundColor = Color.white
			left = 180.pct
			top = (-50).pct
			borderRadius = 40.pct
			transform {
				rotate(60.deg)
			}
			boxShadowInset(offsetX = (-1).px, offsetY = (-3).px, color = Color("#c9c9c9"))
		}
	}

	val seeds2 by css {
		width = 10.pct
		height = 15.pct
		position = Position.absolute
		backgroundColor = Color.white
		left = 64.pct
		top = 50.pct
		borderRadius = 40.pct
		transform {
			rotate(10.deg)
		}
		boxShadowInset(offsetX = (-3).px, offsetY = 0.px, color = Color("#c9c9c9"))

		before {
			content = "".quoted
			width = 100.pct
			height = 100.pct
			position = Position.absolute
			backgroundColor = Color.white
			left = 150.pct
			top = (-130).pct
			borderRadius = 40.pct
			transform {
				rotate(90.deg)
			}
			boxShadowInset(offsetX = 1.px, offsetY = 3.px, color = Color("#c9c9c9"))
		}
	}

	val salad by css {
		width = 85.pct
		height = 7.pct
		margin(2.pct, LinearDimension.auto)
		background = "linear-gradient(#228c1d, #91ce50)"
		borderRadius = 20.px
	}

	val bacon by css {
		width = 80.pct
		height = 3.pct
		margin(2.pct, LinearDimension.auto)
		background = "linear-gradient(#bf3813, #c45e38)"
	}

	val cheese by css {
		width = 90.pct
		height = 4.5.pct
		margin(2.pct, LinearDimension.auto)
		background = "linear-gradient(#f4d004, #d6bb22)"
		borderRadius = 20.px
	}

	val meat by css {
		width = 80.pct
		height = 8.pct
		margin(2.pct, LinearDimension.auto)
		background = "linear-gradient(#7f3608, #702e05)"
		borderRadius = 15.px
	}

	val bottomBun by css {
		height = 13.pct
		width = 80.pct
		background = "linear-gradient(#F08E4A, #e27b36)"
		borderBottomRightRadius = 30.px
		borderBottomLeftRadius = 30.px
		boxShadowInset(offsetX = (-15).px, offsetY = 0.px, color = Color("#c15711"))
		margin(2.pct, LinearDimension.auto)
	}

}
