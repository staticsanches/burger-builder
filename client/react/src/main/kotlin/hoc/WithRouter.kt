package hoc

import react.RProps
import react.router.dom.RouteResultProps
import react.router.dom.rawWithRouter

fun <P : RouteResultProps<T>, T : RProps> withRouter(): HOC<P> = hoc@{ component ->
	return@hoc rawWithRouter<P>(component).also {
		it.displayName = hocDisplayName("withRouter", component)
	}
}
