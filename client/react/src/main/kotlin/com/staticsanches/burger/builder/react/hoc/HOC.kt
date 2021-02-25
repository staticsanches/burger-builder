package com.staticsanches.burger.builder.react.hoc

import com.staticsanches.burger.builder.react.utils.RClassDelegate
import react.Component
import react.RClass
import react.RProps
import react.rClass
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

typealias HOC<P, R> = (RClass<P>) -> RClass<R>

fun hocDisplayName(hoc: String, component: RClass<*>): String =
	hoc.capitalize() + "(" + component.displayName + ")"

fun KProperty<*>.hocDisplayName(component: RClass<*>): String =
	hocDisplayName(this.name, component)

/**
 * Allows the combination of two [HOC]s.
 */
operator fun <P : RProps, R : RProps, Q : RProps> HOC<P, R>.plus(hoc: HOC<R, Q>): HOC<P, Q> =
	{ hoc(this(it)) }

infix fun <P : RProps, R : RProps> RClassDelegate<P>.wrappedBy(hoc: HOC<P, R>): RClassDelegate<R> =
	object : RClassDelegate<R> {

		var name: String? = null
		lateinit var value: RClass<R>

		override fun getValue(thisRef: Any?, property: KProperty<*>): RClass<R> {
			if (!this::value.isInitialized) {
				val originalValue = this@wrappedBy.getValue(thisRef, property)
				value = hoc(originalValue)
				name = value.displayName
			}
			return value
		}

		override fun toString(): String {
			return this.name ?: "Uninitialized HOC"
		}

	}

infix fun <P : RProps, R : RProps> KClass<out Component<P, *>>.wrappedBy(hoc: HOC<P, R>): RClass<R> =
	hoc(this.rClass)
