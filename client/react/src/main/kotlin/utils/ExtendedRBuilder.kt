package utils

import components.ui.CustomInputConfig
import components.ui.CustomInputProps
import components.ui.CustomInputType
import react.RBuilder

/**
 * Workaround for multiple receivers.
 * [Article](https://medium.com/swlh/multiple-receiver-extension-functions-in-kotlin-11becfe880e3)
 */
class ExtendedRBuilder<RB : RBuilder>(private val builder: RB) {

	operator fun <C : CustomInputConfig> CustomInputType<C>.invoke(block: CustomInputProps<C>.() -> Unit) {
		createElement(builder, block)
	}

}

fun <RB : RBuilder> withExtendedRBuilder(builder: RB, block: ExtendedRBuilder<RB>.() -> Unit) {
	ExtendedRBuilder(builder).run(block)
}
