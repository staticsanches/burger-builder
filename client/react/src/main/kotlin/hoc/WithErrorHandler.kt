package hoc

import components.ui.modal
import external.axios.AxiosInstance
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import utils.EventHandler
import utils.FactoryHOC
import utils.setState

fun <P : RProps> withErrorHandler(axios: AxiosInstance): HOC<P> {
	val withErrorHandler by FactoryHOC<P> { component, initialProps ->
		object : RComponent<P, WithErrorHandlerState>(initialProps) {

			val requestInterceptor: Number = axios.interceptors.request.use {
				this.setState {
					error = null
				}
				return@use it
			}
			val responseInterceptor: Number = axios.interceptors.response.use({ it }, {
				this.setState {
					error = it
				}
			})

			val errorConfirmedHandler: EventHandler = {
				this.setState {
					error = null
				}
			}

			override fun componentWillUnmount() {
				axios.interceptors.request.eject(requestInterceptor)
				axios.interceptors.response.eject(responseInterceptor)
			}

			override fun RBuilder.render() {
				val error = state.error
				modal {
					attrs {
						show = error != null
						modalClosed = errorConfirmedHandler
					}
					if (error != null) {
						+(error.asDynamic().message as String)
					}
				}
				child(component, props) {}
			}

		}
	}
	return withErrorHandler
}

private interface WithErrorHandlerState : RState {

	var error: Any?

}
