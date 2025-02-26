package com.hussein.androidprojectstandard.data.repository

import com.hussein.androidprojectstandard.data.base.BaseRepository
import com.hussein.androidprojectstandard.data.datasource.remote.http.service.AuthService
import com.hussein.androidprojectstandard.data.model.http.auth.LoginResponse
import com.hussein.androidprojectstandard.domain.base.Result
import com.hussein.androidprojectstandard.domain.model.User
import com.hussein.androidprojectstandard.utils.UIText
import kotlinx.coroutines.delay

class MainRepository(private val mainService: AuthService) : BaseRepository() {

    //region HTTP
    suspend fun login(usernameOrEmailAddress: String, password: String): Result<LoginResponse> {

        // This how it should be, but for mimicking remote request, we hardcode the result
//        return sendRemoteRequest {
//            mainService.login(AuthResources.Login(), LoginRequest(usernameOrEmailAddress, password))
//        }


        // Mimicking the time to connect to remote server
        delay(2000)
        return Result.success(LoginResponse("token", User(id = 2, name = "Alhussein Alzuhile")))
        // For mimicking failure:
//        return Result.failure(UIText.Hardcoded("This is mimicked failure"))
    }
    //endregion

    //region MQTT

    //endregion
}

