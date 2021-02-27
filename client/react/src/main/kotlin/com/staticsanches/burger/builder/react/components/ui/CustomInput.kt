package com.staticsanches.burger.builder.react.components.ui

import com.staticsanches.burger.builder.react.hoc.defaultProps
import com.staticsanches.burger.builder.react.hoc.wrappedBy
import com.staticsanches.burger.builder.react.utils.EventHandler
import com.staticsanches.burger.builder.react.utils.FunctionalComponentDelegate
import kotlinext.js.jsObject
import kotlinx.css.BorderStyle
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.FontWeight
import kotlinx.css.Outline
import kotlinx.css.backgroundColor
import kotlinx.css.boxSizing
import kotlinx.css.display
import kotlinx.css.fontWeight
import kotlinx.css.marginBottom
import kotlinx.css.outline
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.border
import kotlinx.css.px
import kotlinx.css.width
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import react.RBuilder
import react.RProps
import react.dom.option
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledInput
import styled.styledLabel
import styled.styledSelect

interface CustomInputConfig

sealed class CustomInputType<C : CustomInputConfig> {

	fun createElement(builder: RBuilder, block: CustomInputProps<C>.() -> Unit) {
		val props: InputProps<C> = jsObject()
		props.block()
		props.inputType = this
		builder.child(input, props) {}
	}

}

object CustomInput : CustomInputType<CustomInput.Config>() {

	data class Config(
		val type: InputType, val placeholder: String = ""
	) : CustomInputConfig

}

object CustomSelect : CustomInputType<CustomSelect.Config>() {

	data class Option(
		val label: String, val value: String
	)

	data class Config(
		val options: List<Option>
	) : CustomInputConfig

}

interface CustomInputProps<C : CustomInputConfig> : RProps {

	var label: String?

	var inputConfig: C
	var inputValue: String

	var changed: EventHandler

	var touched: Boolean
	var invalid: Boolean

}

private interface InputProps<C : CustomInputConfig> : CustomInputProps<C> {

	var inputType: CustomInputType<C>

}

private val input by FunctionalComponentDelegate<InputProps<*>> { props ->
	styledDiv {
		css { +CustomInputStyles.input }
		styledLabel {
			css { +CustomInputStyles.label }
			val label = props.label
			if (label != null) {
				+label
			}
		}
		when (props.inputType) {
			CustomInput -> {
				val config = props.inputConfig as CustomInput.Config
				styledInput {
					css {
						+CustomInputStyles.inputElement
						if (props.touched && props.invalid) {
							+CustomInputStyles.invalid
						}
					}
					attrs {
						type = config.type
						placeholder = config.placeholder
						value = props.inputValue
						onChangeFunction = props.changed
					}
				}
			}
			CustomSelect -> {
				val config = props.inputConfig as CustomSelect.Config
				styledSelect {
					css {
						+CustomInputStyles.inputElement
						if (props.touched && props.invalid) {
							+CustomInputStyles.invalid
						}
					}
					attrs {
						value = props.inputValue
						onChangeFunction = props.changed
					}
					config.options.renderEach { option ->
						option {
							attrs {
								key = option.value
								value = option.value
							}
							+option.label
						}
					}
				}
			}
		}
	}
} wrappedBy defaultProps { touched = false; invalid = false }

private object CustomInputStyles : StyleSheet("CustomInputStyles") {

	val input by css {
		width = 100.pct
		padding(10.px)
		boxSizing = BoxSizing.borderBox
	}

	val label by css {
		fontWeight = FontWeight.bold
		display = Display.block
		marginBottom = 8.px
	}

	val inputElement by css {
		outline = Outline.none
		border(1.px, BorderStyle.solid, Color("#ccc"))
		backgroundColor = Color.white
		put("font", "inherit")
		padding(6.px, 10.px)
		display = Display.block
		width = 100.pct
		boxSizing = BoxSizing.borderBox

		focus {
			outline = Outline.none
			backgroundColor = Color("#ccc")
		}
	}

	val invalid by css {
		border(1.px, BorderStyle.solid, Color.red)
		backgroundColor = Color("#FDA49A")
	}

}
