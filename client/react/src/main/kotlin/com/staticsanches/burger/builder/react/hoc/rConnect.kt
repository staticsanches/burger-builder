package com.staticsanches.burger.builder.react.hoc

import com.staticsanches.burger.builder.react.store.AppState
import com.staticsanches.burger.builder.react.utils.Dispatch
import kotlinext.js.jsObject
import react.RProps
import react.invoke
import react.redux.Options
import react.redux.connect
import redux.RAction
import redux.WrapperAction

typealias MapStateToProps<P> = P.(state: AppState) -> Unit
typealias MapStateToPropsWithOwnProps<P, OP> = P.(state: AppState, ownProps: OP) -> Unit

typealias MapDispatchToProps<P> = P.(dispatch: Dispatch) -> Unit
typealias MapDispatchToPropsWithOwnProps<P, OP> = P.(dispatch: Dispatch, ownProps: OP) -> Unit

fun <P : RProps, OP : RProps> rConnect(
	mapStateToProps: MapStateToProps<P>,
	options: (Options<AppState, OP, P, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<AppState, Any, Any, OP, P, RProps, P>(
		{ appState, _ ->
			jsObject {
				mapStateToProps(appState)
			}
		},
		undefined,
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}

fun <P : RProps, OP : RProps> rConnect(
	mapStateToProps: MapStateToPropsWithOwnProps<P, OP>,
	options: (Options<AppState, OP, P, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<AppState, Any, Any, OP, P, RProps, P>(
		{ appState, ownProps ->
			jsObject {
				mapStateToProps(appState, ownProps)
			}
		},
		undefined,
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}

fun <P : RProps, OP : RProps> rConnect(
	mapDispatchToProps: MapDispatchToProps<P>,
	options: (Options<Any, OP, RProps, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<Any, RAction, WrapperAction, OP, RProps, P, P>(
		undefined,
		{ dispatch, _ ->
			jsObject {
				mapDispatchToProps(dispatch)
			}
		},
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}

fun <P : RProps, OP : RProps> rConnect(
	mapDispatchToProps: MapDispatchToPropsWithOwnProps<P, OP>,
	options: (Options<Any, OP, RProps, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<Any, RAction, WrapperAction, OP, RProps, P, P>(
		undefined,
		{ dispatch, ownProps ->
			jsObject {
				mapDispatchToProps(dispatch, ownProps)
			}
		},
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}

fun <P : RProps, OP : RProps> rConnect(
	mapStateToProps: MapStateToProps<P>,
	mapDispatchToProps: MapDispatchToProps<P>,
	options: (Options<AppState, OP, P, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<AppState, RAction, WrapperAction, OP, P, P, P>(
		{ appState, _ ->
			jsObject {
				mapStateToProps(appState)
			}
		},
		{ dispatch, _ ->
			jsObject {
				mapDispatchToProps(dispatch)
			}
		},
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}

fun <P : RProps, OP : RProps> rConnect(
	mapStateToProps: MapStateToProps<P>,
	mapDispatchToProps: MapDispatchToPropsWithOwnProps<P, OP>,
	options: (Options<AppState, OP, P, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<AppState, RAction, WrapperAction, OP, P, P, P>(
		{ appState, _ ->
			jsObject {
				mapStateToProps(appState)
			}
		},
		{ dispatch, ownProps ->
			jsObject {
				mapDispatchToProps(dispatch, ownProps)
			}
		},
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}

fun <P : RProps, OP : RProps> rConnect(
	mapStateToProps: MapStateToPropsWithOwnProps<P, OP>,
	mapDispatchToProps: MapDispatchToProps<P>,
	options: (Options<AppState, OP, P, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<AppState, RAction, WrapperAction, OP, P, P, P>(
		{ appState, ownProps ->
			jsObject {
				mapStateToProps(appState, ownProps)
			}
		},
		{ dispatch, _ ->
			jsObject {
				mapDispatchToProps(dispatch)
			}
		},
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}

fun <P : RProps, OP : RProps> rConnect(
	mapStateToProps: MapStateToPropsWithOwnProps<P, OP>,
	mapDispatchToProps: MapDispatchToPropsWithOwnProps<P, OP>,
	options: (Options<AppState, OP, P, P>.() -> Unit) = {}
): HOC<P, OP> = { component ->
	connect<AppState, RAction, WrapperAction, OP, P, P, P>(
		{ appState, ownProps ->
			jsObject {
				mapStateToProps(appState, ownProps)
			}
		},
		{ dispatch, ownProps ->
			jsObject {
				mapDispatchToProps(dispatch, ownProps)
			}
		},
		undefined,
		jsObject {
			getDisplayName = { name: String -> "RConnect($name)" }
			methodName = "rConnect"
			options(this)
		}
	)(component)
}
