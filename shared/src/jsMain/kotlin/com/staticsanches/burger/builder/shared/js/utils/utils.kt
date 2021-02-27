package com.staticsanches.burger.builder.shared.js.utils

fun Number.toFixed(digits: Int): String =
	this.asDynamic().toFixed(digits) as String
