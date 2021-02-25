@file:Suppress("UNCHECKED_CAST", "UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")

package com.staticsanches.burger.builder.react.hoc

import com.staticsanches.burger.builder.react.utils.RClassDelegate
import react.RClass
import react.RProps

/**
 * Useful when the delegate [RProps] should not be visible by the caller since some properties are added externally,
 * for example, by the [withRouter] [HOC].
 */
fun <R : RProps> unsafePropsCast(): HOC<*, R> = { it as RClass<R> }

/**
 * Useful when the delegate [RProps] should not be visible by the caller since some properties are added externally,
 * for example, the route component.
 */
fun <R : RProps> RClassDelegate<*>.unsafePropsCast(): RClassDelegate<R> =
	this as RClassDelegate<R>
