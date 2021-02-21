package hoc

import external.react.PropsAreEqual
import external.react.memo
import react.RProps


fun <P : RProps> memo(propsAreEqual: PropsAreEqual<P>): HOC<P> = hoc@{ component ->
	return@hoc memo(component, propsAreEqual)
		.also { it.displayName = hocDisplayName("memo", component) }
}
