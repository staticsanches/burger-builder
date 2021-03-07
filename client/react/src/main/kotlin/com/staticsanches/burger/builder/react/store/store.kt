package com.staticsanches.burger.builder.react.store

import com.staticsanches.burger.builder.react.utils.rThunk
import kotlinext.js.Object
import kotlinext.js.assign
import kotlinext.js.js
import redux.*

@Suppress("UnsafeCastFromDynamic")
val store = createStore<AppState, RAction, dynamic>(
	combinedReducers,
	AppState(),
	compose(
		rThunk(),
		customEnhancer(),
		js("if (window.__REDUX_DEVTOOLS_EXTENSION__ ) window.__REDUX_DEVTOOLS_EXTENSION__(); else (function(f){return f;});")
	)
)

// Credit: https://medium.com/@night.crawler/a-fresh-start-with-kotlin-js-and-react-redux-subtleties-1-7519dee298d1
private fun <S> customEnhancer(): Enhancer<S, Action, Action, RAction, WrapperAction> = { next ->
	{ reducer, initialState ->
		fun wrapperReducer(reducer: Reducer<S, RAction>): Reducer<S, WrapperAction> =
			{ state, action ->
				if (!action.asDynamic().isKotlin as Boolean) {
					reducer(state, action.asDynamic().unsafeCast<RAction>())
				} else {
					reducer(state, action.action)
				}
			}

		val nextStoreCreator = next.unsafeCast<StoreCreator<S, WrapperAction, WrapperAction>>()
		val store = nextStoreCreator(
			wrapperReducer(reducer),
			// we need this cast to get rid of annoying type check performed by redux itself:
			// The previous state received by the reducer has unexpected type of "Object".
			// Expected argument to be an object with the following keys: "appPreferences", "router"
			Object.assign(js {}, initialState!!) as S
		)

		assign(Object.assign(js {}, store)) {
			dispatch = { action: dynamic ->
				// original redux actions use `type` keyword, so we don't reshape them
				if (action.type != undefined && action.action == undefined) {
					store.dispatch(action.unsafeCast<WrapperAction>())
				} else {
					// it's a Kotlin action, so we'll reshape it and provide a marker for the wrapper
					store.dispatch(js {
						type = action::class.simpleName
						isKotlin = true
						this.action = action
					}.unsafeCast<WrapperAction>())
				}
			}
			replaceReducer = { nextReducer: Reducer<S, RAction> ->
				store.replaceReducer(wrapperReducer(nextReducer))
			}
		}.unsafeCast<Store<S, RAction, WrapperAction>>()
	}
}
