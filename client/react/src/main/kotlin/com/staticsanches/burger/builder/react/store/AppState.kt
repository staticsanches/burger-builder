package com.staticsanches.burger.builder.react.store

import com.staticsanches.burger.builder.react.store.slices.BurgerSlice

data class AppState(
	val burgerState: BurgerSlice.State = BurgerSlice.initialState
)
