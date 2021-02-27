package com.staticsanches.burger.builder.react.utils

import com.staticsanches.burger.builder.react.store.AppState
import kotlinext.js.jsObject
import redux.RAction
import redux.WrapperAction
import redux.applyMiddleware

interface RThunk : RAction {

	operator fun invoke(dispatch: Dispatch, getState: () -> AppState): WrapperAction

}

// credit: https://github.com/AltmanEA/KotlinExamples
fun rThunk() =
	applyMiddleware<AppState, RAction, WrapperAction, RAction, WrapperAction>(
		{ store ->
			{ next ->
				{ action ->
					if (action is RThunk) {
						action(store::dispatch, store::getState)
					} else {
						next(action)
					}
				}
			}
		}
	)

val nullAction = jsObject<WrapperAction>()
