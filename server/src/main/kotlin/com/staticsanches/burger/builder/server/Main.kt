package com.staticsanches.burger.builder.server

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

	install(Compression)

	routing {

		get("/") {
			call.respondText("Hello World from Server!", contentType = ContentType.Text.Plain)
		}

	}

}
