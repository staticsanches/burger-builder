@file:JsModule("axios")
@file:JsNonModule
@file:Suppress(
	"UNUSED",
	"OVERRIDING_FINAL_MEMBER",
	"RETURN_TYPE_MISMATCH_ON_OVERRIDE",
	"CONFLICTING_OVERLOADS"
)

package external.axios

import kotlin.js.Promise

external interface AxiosRequestConfig {

	var url: String?
		get() = definedExternally
		set(value) = definedExternally
	var method: String? /* "get" | "GET" | "delete" | "DELETE" | "head" | "HEAD" | "options" | "OPTIONS" | "post" | "POST" | "put" | "PUT" | "patch" | "PATCH" | "purge" | "PURGE" | "link" | "LINK" | "unlink" | "UNLINK" */
		get() = definedExternally
		set(value) = definedExternally
	var baseURL: String?
		get() = definedExternally
		set(value) = definedExternally
	var transformRequest: dynamic /* AxiosTransformer? | Array<AxiosTransformer>? */
		get() = definedExternally
		set(value) = definedExternally
	var transformResponse: dynamic /* AxiosTransformer? | Array<AxiosTransformer>? */
		get() = definedExternally
		set(value) = definedExternally
	var headers: Any?
		get() = definedExternally
		set(value) = definedExternally
	var params: Any?
		get() = definedExternally
		set(value) = definedExternally
	var paramsSerializer: ((params: Any) -> String)?
		get() = definedExternally
		set(value) = definedExternally
	var data: Any?
		get() = definedExternally
		set(value) = definedExternally
	var timeout: Number?
		get() = definedExternally
		set(value) = definedExternally
	var timeoutErrorMessage: String?
		get() = definedExternally
		set(value) = definedExternally
	var withCredentials: Boolean?
		get() = definedExternally
		set(value) = definedExternally
	var responseType: String? /* "arraybuffer" | "blob" | "document" | "json" | "text" | "stream" */
		get() = definedExternally
		set(value) = definedExternally
	var xsrfCookieName: String?
		get() = definedExternally
		set(value) = definedExternally
	var xsrfHeaderName: String?
		get() = definedExternally
		set(value) = definedExternally
	var onUploadProgress: ((progressEvent: Any) -> Unit)?
		get() = definedExternally
		set(value) = definedExternally
	var onDownloadProgress: ((progressEvent: Any) -> Unit)?
		get() = definedExternally
		set(value) = definedExternally
	var maxContentLength: Number?
		get() = definedExternally
		set(value) = definedExternally
	var validateStatus: ((status: Number) -> Boolean)?
		get() = definedExternally
		set(value) = definedExternally
	var maxBodyLength: Number?
		get() = definedExternally
		set(value) = definedExternally
	var maxRedirects: Number?
		get() = definedExternally
		set(value) = definedExternally
	var socketPath: String?
		get() = definedExternally
		set(value) = definedExternally
	var httpAgent: Any?
		get() = definedExternally
		set(value) = definedExternally
	var httpsAgent: Any?
		get() = definedExternally
		set(value) = definedExternally
	var proxy: dynamic /* AxiosProxyConfig? | Boolean? */
		get() = definedExternally
		set(value) = definedExternally
	var decompress: Boolean?
		get() = definedExternally
		set(value) = definedExternally

}

external interface AxiosResponse<T> {

	var data: T
	var status: Number
	var statusText: String
	var headers: Any
	var config: AxiosRequestConfig
	var request: Any?
		get() = definedExternally
		set(value) = definedExternally

}

external interface AxiosInterceptorManager<V> {

	fun use(
		onFulfilled: (value: V) -> Any? = definedExternally,
		onRejected: (error: Any) -> Any = definedExternally
	): Number

	fun eject(id: Number)

}

external interface AxiosInterceptors {

	var request: AxiosInterceptorManager<AxiosRequestConfig>
	var response: AxiosInterceptorManager<AxiosResponse<*>>

}

external interface AxiosInstance {

	var defaults: AxiosRequestConfig
	var interceptors: AxiosInterceptors
	fun getUri(config: AxiosRequestConfig = definedExternally): String
	fun <R> request(config: AxiosRequestConfig): Promise<R>
	fun <R> get(url: String, config: AxiosRequestConfig = definedExternally): Promise<AxiosResponse<R>>
	fun <R> delete(url: String, config: AxiosRequestConfig = definedExternally): Promise<R>
	fun <R> head(url: String, config: AxiosRequestConfig = definedExternally): Promise<R>
	fun <R> options(url: String, config: AxiosRequestConfig = definedExternally): Promise<R>
	fun <R> post(url: String, data: Any = definedExternally, config: AxiosRequestConfig = definedExternally): Promise<R>
	fun <R> put(url: String, data: Any = definedExternally, config: AxiosRequestConfig = definedExternally): Promise<R>
	fun <R> patch(
		url: String, data: Any = definedExternally, config: AxiosRequestConfig = definedExternally
	): Promise<R>

}

external interface AxiosStatic : AxiosInstance {

	fun create(config: AxiosRequestConfig = definedExternally): AxiosInstance

}

@JsName("default")
external var axios: AxiosStatic
