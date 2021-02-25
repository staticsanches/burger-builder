package com.staticsanches.burger.builder.react.hoc

import external.react.PropsAreEqual
import external.react.memo
import react.RProps


/**
 * [HOC] for React [memo].
 */
fun <P : RProps> memo(propsAreEqual: PropsAreEqual<P>): HOC<P, P> = hoc@{ component ->
	return@hoc memo(component, propsAreEqual)
		.also { it.displayName = hocDisplayName("memo", component) }
}
