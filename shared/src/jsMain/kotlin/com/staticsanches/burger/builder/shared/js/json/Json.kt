package com.staticsanches.burger.builder.shared.js.json

import com.staticsanches.burger.builder.shared.json.fromJson

/**
 * Helper method to allow the conversion of a object that can be serialized to a kotlin one.
 */
inline fun <reified T> Any.convertFromJson(): T =
	JSON.stringify(this).fromJson()
