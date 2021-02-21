package axios

import com.staticsanches.burger.builder.client.commons.firebaseUrl
import external.axios.axios
import kotlinext.js.jsObject

val axiosOrder = axios.create(jsObject {
	baseURL = firebaseUrl
})
