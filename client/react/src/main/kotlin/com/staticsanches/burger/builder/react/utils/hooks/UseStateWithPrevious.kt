package com.staticsanches.burger.builder.react.utils.hooks

import react.RStateDelegate

typealias StateUpdateWithPrevious<S> = (prevState: S) -> S
typealias RSetStateWithPrevious<S> = (StateUpdateWithPrevious<S>) -> Unit

/**
 * Allow the functionality of setting state based on the previous one.
 */
@Suppress("UNCHECKED_CAST")
operator fun <S> RStateDelegate<S>.component3(): RSetStateWithPrevious<S> =
	component2() as RSetStateWithPrevious<S>
