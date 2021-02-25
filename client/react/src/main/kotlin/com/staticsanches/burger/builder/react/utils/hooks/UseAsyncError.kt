package com.staticsanches.burger.builder.react.utils.hooks

import react.useCallback
import react.useState

typealias UseAsyncError = (error: Throwable) -> Unit

/**
 * https://medium.com/trabe/catching-asynchronous-errors-in-react-using-error-boundaries-5e8a5fd7b971
 */
fun useAsyncError(): UseAsyncError {
	val (_, _, setError) = useState(false)
	return useCallback({ e -> setError { throw e } }, arrayOf(setError))
}
