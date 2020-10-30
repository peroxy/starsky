package com.starsky.api

import com.starsky.api.routes.getAuthRoutes
import com.starsky.api.routes.getUserRoutes
import com.starsky.api.security.JwtConfig
import com.starsky.api.security.UserPrincipal
import com.starsky.env.Environment
import com.starsky.env.EnvironmentVars
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging) {
        level = if (EnvironmentVars.environment == Environment.DEV) Level.DEBUG else Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "com.starsky"
            validate {
                val id = it.payload.getClaim("id")?.asInt()
                if (id == null){
                    null
                }else {
                    UserPrincipal(id)
                }

            }
        }
    }

    install(ContentNegotiation) {
        gson {
            if (EnvironmentVars.environment == Environment.DEV){
                setPrettyPrinting()
            }

        }
    }

    getAuthRoutes()
    getUserRoutes()

}
