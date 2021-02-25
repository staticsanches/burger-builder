package com.staticsanches.burger.builder.react.store

import com.staticsanches.burger.builder.react.store.slices.BurgerSlice
import redux.RAction
import redux.Reducer
import redux.combineReducers
import kotlin.reflect.KProperty1

val combinedReducers: Reducer<AppState, RAction> =
	combineReducersInferred(
		mapOf(
			AppState::burgerState reducedBy BurgerSlice::reducer
		)
	)

// credit https://github.com/lawik123/kotlin-poc-frontend-react-redux
private fun <S, A> combineReducersInferred(reducers: Map<KProperty1<S, *>, Reducer<*, A>>): Reducer<S, A> {
	return combineReducers(reducers.mapKeys { it.key.name })
}

private infix fun <S, R, A> KProperty1<S, R>.reducedBy(that: Reducer<R, A>): Pair<KProperty1<S, R>, Reducer<R, A>> =
	Pair(this, that)
