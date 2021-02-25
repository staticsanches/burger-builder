package com.staticsanches.burger.builder.react.hoc

import com.staticsanches.burger.builder.react.utils.EventHandler
import components.ui.modal
import react.RBuilder
import react.RClass
import react.RComponent
import react.RErrorInfo
import react.RProps
import react.RState
import utils.setState

fun <P : RProps> withErrorBoundary(fallback: RBuilder.() -> Unit): HOC<P, P> {
	val withErrorBoundary by FactoryHOCDelegate<P> { component, initialProps ->
		return@FactoryHOCDelegate object : WithErrorBoundary<P>(initialProps) {

			override val component: RClass<P>
				get() = component

			override fun RBuilder.renderFallback(error: Throwable, info: RErrorInfo) {
				fallback()
			}

		}
	}
	return withErrorBoundary
}

fun <P : RProps> withErrorBoundaryReceivingInfo(fallback: RBuilder.(error: Throwable, info: RErrorInfo) -> Unit): HOC<P, P> {
	val withErrorBoundary by FactoryHOCDelegate<P> { component, initialProps ->
		return@FactoryHOCDelegate object : WithErrorBoundary<P>(initialProps) {

			override val component: RClass<P>
				get() = component

			override fun RBuilder.renderFallback(error: Throwable, info: RErrorInfo) {
				fallback(error, info)
			}

		}
	}
	return withErrorBoundary
}

private interface WithErrorBoundaryState : RState {

	var errorOccurred: Boolean
	var showModal: Boolean
	var error: Throwable
	var info: RErrorInfo

}

private abstract class WithErrorBoundary<P : RProps>(initialProps: P) :
	RComponent<P, WithErrorBoundaryState>(initialProps) {

	abstract val component: RClass<P>

	override fun WithErrorBoundaryState.init(props: P) {
		this.errorOccurred = false
		this.showModal = false
	}

	override fun componentDidCatch(error: Throwable, info: RErrorInfo) {
		this.setState {
			errorOccurred = true
			showModal = true
			this.error = error
			this.info = info
		}
	}

	val errorConfirmedHandler: EventHandler = {
		this.setState { showModal = false }
	}

	abstract fun RBuilder.renderFallback(error: Throwable, info: RErrorInfo)

	override fun RBuilder.render() {
		val errorMessage = if (state.errorOccurred) state.error.message else null
		modal {
			attrs {
				show = state.showModal
				modalClosed = errorConfirmedHandler
			}
			if (errorMessage != null) {
				+errorMessage
			}
		}
		if (state.errorOccurred) {
			renderFallback(state.error, state.info)
		} else {
			child(component, props) {}
		}
	}

}
