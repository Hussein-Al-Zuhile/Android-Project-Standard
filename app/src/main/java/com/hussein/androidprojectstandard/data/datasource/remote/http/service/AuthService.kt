package com.hussein.androidprojectstandard.data.datasource.remote.http.service

import com.hussein.androidprojectstandard.data.base.BaseRemoteService
import com.hussein.androidprojectstandard.data.datasource.remote.http.AuthResources
import com.hussein.androidprojectstandard.data.model.http.auth.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody


class AuthService(client: HttpClient) : BaseRemoteService(client) {

    suspend fun login(login: AuthResources.Login, loginRequest: LoginRequest) =
        post(login) {
            setBody(loginRequest)
        }

}