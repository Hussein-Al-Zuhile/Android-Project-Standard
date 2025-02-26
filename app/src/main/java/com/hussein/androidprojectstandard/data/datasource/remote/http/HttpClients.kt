package com.hussein.androidprojectstandard.data.datasource.remote.http

import android.util.Log
import com.hussein.androidprojectstandard.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val ExampleHttpClient = HttpClient(Android) {
    // This will throw exception if response status is not 2xx
    expectSuccess = true

    developmentMode = BuildConfig.DEBUG

    install(Resources)

    engine {
        connectTimeout = 20_000
        socketTimeout = 20_000
    }

    if (BuildConfig.DEBUG) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("HTTP Logging", message)
                }
            }
            level = LogLevel.ALL
        }
    }

    defaultRequest {
        url("${BuildConfig.EXAMPLE_API_BASE_URL}/api/")
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        })
    }
}