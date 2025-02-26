package com.hussein.androidprojectstandard.data.base

import com.hussein.androidprojectstandard.data.datasource.remote.http.ApiResources
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse

abstract class BaseRemoteService(protected val client: HttpClient) {

    protected suspend inline fun <reified T : ApiResources> get(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse =
        client.get(resource, builder)

    protected suspend inline fun <reified T : ApiResources> post(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        client.submitForm()
        return client.post(resource, builder)
    }

    protected suspend inline fun <reified T : ApiResources> put(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse =
        client.put(resource, builder)
}