package com.hussein.androidprojectstandard.data.datasource.remote.http

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable


@Serializable
open class ApiResources


@Resource("/TokenAuth")
class AuthResources {
    @Resource("Authenticate")
    class Login(
        val parent: AuthResources = AuthResources(),
    ) : ApiResources()
}