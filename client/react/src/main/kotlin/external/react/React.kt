@file:JsModule("react")
@file:JsNonModule

package external.react

import react.FunctionalComponent
import react.RClass
import react.RProps

// Memo (16.6+)
external fun <P : RProps> memo(fc: FunctionalComponent<P>, propsAreEqual: PropsAreEqual<P>): FunctionalComponent<P>
external fun <P : RProps> memo(rClass: RClass<P>, propsAreEqual: PropsAreEqual<P>): RClass<P>
