package external.styled

import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.TagConsumer
import react.RBuilder
import styled.StyledDOMBuilder
import styled.styledTag

open class MAIN(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
	HTMLTag("main", consumer, initialAttributes, null, false, false),
	HtmlBlockTag

inline fun RBuilder.styledMain(block: StyledDOMBuilder<MAIN>.() -> Unit) =
	styledTag(block) { MAIN(kotlinx.html.emptyMap, it) }
