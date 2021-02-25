package containers

import axios.axiosOrder
import com.staticsanches.burger.builder.react.components.ui.ButtonType
import com.staticsanches.burger.builder.react.components.ui.button
import components.burger.BurgerIngredients
import components.ui.CustomInput
import components.ui.CustomInputConfig
import components.ui.CustomSelect
import components.ui.spinner
import kotlinext.js.js
import kotlinx.css.BorderStyle
import kotlinx.css.BoxSizing
import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.TextAlign
import kotlinx.css.boxSizing
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import kotlinx.css.px
import kotlinx.css.textAlign
import kotlinx.css.width
import kotlinx.html.InputType
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.dom.form
import react.dom.h4
import react.key
import react.rClass
import react.router.dom.RouteResultProps
import styled.css
import styled.styledDiv
import utils.EventHandler
import utils.setState
import utils.withExtendedRBuilder
import kotlin.reflect.KMutableProperty1

interface ContactDataProps : RouteResultProps<RProps> {

	var ingredients: BurgerIngredients
	var price: Number

}

val contactData: RClass<ContactDataProps> = ContactData::class.rClass

private interface ContactDataState : RState {

	var orderForm: OrderForm
	var validForm: Boolean
	var loading: Boolean

}

private class ContactData(initialProps: ContactDataProps) :
	RComponent<ContactDataProps, ContactDataState>(initialProps) {

	override fun ContactDataState.init(props: ContactDataProps) {
		orderForm = OrderForm()
		validForm = false
		loading = false
	}

	val orderHandler: EventHandler = { event ->
		event.preventDefault()
		this.setState { loading = true }
		val orderForm = state.orderForm
		val formData = js { }
		OrderForm.allFieldsAccessors.forEach { field ->
			formData[field.name] = field.get(orderForm).value
		}
		val order = js {
			ingredients = this@ContactData.props.ingredients
			price = this@ContactData.props.price
			orderData = formData
		} as Any
		axiosOrder.post<Any>("/orders.json", order)
			.then {
				this.setState { loading = false }
				this.props.history.push("/")
			}
			.catch {
				this.setState { loading = false }
			}
	}

	fun <F : FormField<F, C>, C : CustomInputConfig> inputChangedHandler(
		event: Event, fieldAccessor: KMutableProperty1<OrderForm, F>
	) {
		val newValue = event.target?.asDynamic()?.value as String? ?: ""
		val newOrderForm = state.orderForm.copy()
		fieldAccessor.set(newOrderForm, fieldAccessor.get(newOrderForm).copy(newValue, true))

		val validForm = OrderForm.allFieldsAccessors.all { it.get(newOrderForm).valid }
		this.setState { orderForm = newOrderForm; this.validForm = validForm }
	}

	@Suppress("UNCHECKED_CAST")
	override fun RBuilder.render() {
		styledDiv {
			css {
				margin(20.px, LinearDimension.auto)
				width = 80.pct
				textAlign = TextAlign.center
				boxShadow(offsetX = 0.px, offsetY = 2.px, blurRadius = 3.px, color = Color("#ccc"))
				border(width = 1.px, style = BorderStyle.solid, color = Color("#eee"))
				padding(10.px)
				boxSizing = BoxSizing.borderBox

				media("(min-width: 600px)") {
					width = 500.px
				}
			}

			h4 { +"Enter your Contact Data" }
			if (state.loading) {
				spinner {}
			} else {
				form {
					attrs.onSubmitFunction = orderHandler

					val orderForm = state.orderForm
					OrderForm.allFieldsAccessors.renderEach { accessor ->
						when (val field = accessor.get(orderForm)) {
							is InputField -> {
								withExtendedRBuilder(this) {
									CustomInput {
										key = accessor.name
										inputConfig = field.config
										inputValue = field.value
										changed = { event ->
											inputChangedHandler(
												event,
												accessor as KMutableProperty1<OrderForm, InputField>
											)
										}
										touched = field.touched
										invalid = !field.valid
									}
								}
							}
							is SelectField -> {
								withExtendedRBuilder(this) {
									CustomSelect {
										key = accessor.name
										inputConfig = field.config
										inputValue = field.value
										changed = { event ->
											inputChangedHandler(
												event,
												accessor as KMutableProperty1<OrderForm, SelectField>
											)
										}
										touched = field.touched
										invalid = !field.valid
									}
								}
							}
						}
					}
					button {
						attrs {
							buttonType = ButtonType.SUCCESS
							disabled = !state.validForm
						}
						+"ORDER"
					}
				}
			}
		}
	}

}

private interface ValidationRule {

	fun isValid(value: String): Boolean

}

private object Required : ValidationRule {

	override fun isValid(value: String): Boolean =
		value.trim() != ""

}

private class MinLength(val minLength: Int) : ValidationRule {

	override fun isValid(value: String): Boolean =
		value.trim().length >= minLength

}

private class MaxLength(val maxLength: Int) : ValidationRule {

	override fun isValid(value: String): Boolean =
		value.trim().length <= maxLength

}

private sealed class FormField<F : FormField<F, C>, C : CustomInputConfig> {

	abstract val config: C
	abstract val validationRules: List<ValidationRule>

	abstract val value: String
	abstract val valid: Boolean
	abstract val touched: Boolean

	abstract fun copy(newValue: String, touched: Boolean): F

}

private class InputField(
	override val config: CustomInput.Config,
	override val validationRules: List<ValidationRule>,
	override val value: String,
	override val valid: Boolean,
	override val touched: Boolean
) : FormField<InputField, CustomInput.Config>() {

	override fun copy(newValue: String, touched: Boolean): InputField {
		val valid = validationRules.all { it.isValid(newValue) }
		return InputField(config, validationRules, newValue, valid, touched)
	}

}

private class SelectField(
	override val config: CustomSelect.Config,
	override val validationRules: List<ValidationRule>,
	override val value: String,
	override val valid: Boolean,
	override val touched: Boolean
) : FormField<SelectField, CustomSelect.Config>() {

	override fun copy(newValue: String, touched: Boolean): SelectField {
		val valid = validationRules.all { it.isValid(newValue) }
		return SelectField(config, validationRules, newValue, valid, touched)
	}

}

private data class OrderForm(
	var name: InputField = InputField(
		config = CustomInput.Config(InputType.text, "Your Name"),
		validationRules = listOf(Required),
		value = "",
		valid = false,
		touched = false
	),
	var street: InputField = InputField(
		config = CustomInput.Config(InputType.text, "Street"),
		validationRules = listOf(Required),
		value = "",
		valid = false,
		touched = false
	),
	var postalCode: InputField = InputField(
		config = CustomInput.Config(InputType.text, "Postal Code"),
		validationRules = listOf(Required, MinLength(5), MaxLength(5)),
		value = "",
		valid = false,
		touched = false
	),
	var country: InputField = InputField(
		config = CustomInput.Config(InputType.text, "Country"),
		validationRules = listOf(Required),
		value = "",
		valid = false,
		touched = false
	),
	var email: InputField = InputField(
		config = CustomInput.Config(InputType.email, "Your E-mail"),
		validationRules = listOf(Required),
		value = "",
		valid = false,
		touched = false
	),
	var deliveryMethod: SelectField = SelectField(
		config = CustomSelect.Config(
			listOf(
				CustomSelect.Option("Cheapest", "cheapest"),
				CustomSelect.Option("Fastest", "fastest")
			)
		),
		validationRules = emptyList(),
		value = "fastest",
		valid = true,
		touched = false
	)
) {

	companion object {

		val allFieldsAccessors = listOf(
			OrderForm::name,
			OrderForm::street,
			OrderForm::postalCode,
			OrderForm::country,
			OrderForm::email,
			OrderForm::deliveryMethod
		)

	}

}
