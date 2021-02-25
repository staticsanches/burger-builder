package com.staticsanches.burger.builder.react.utils

import react.RClass
import react.RProps
import kotlin.reflect.KProperty

/**
 * Delegates the creation of a new [RClass] instance.
 */
interface RClassDelegate<P : RProps> {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): RClass<P>

}
