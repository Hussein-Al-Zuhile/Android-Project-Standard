package com.hussein.androidprojectstandard.domain.usecase.http.auth

import com.hussein.androidprojectstandard.data.model.http.auth.LoginResponse
import com.hussein.androidprojectstandard.data.repository.MainRepository
import com.hussein.androidprojectstandard.domain.base.BaseUseCase
import com.hussein.androidprojectstandard.domain.base.Result

class LoginUseCase(private val repository: MainRepository) : BaseUseCase<LoginResponse>() {

    operator fun invoke(usernameOrEmailAddress: String, password: String) = execute {
        repository.login(usernameOrEmailAddress, password)
    }
}