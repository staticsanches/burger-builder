package com.staticsanches.burger.builder.react.utils

import org.w3c.dom.events.Event
import redux.RAction
import redux.WrapperAction

typealias EventHandler = (Event) -> Unit

typealias Dispatch = (RAction) -> WrapperAction
