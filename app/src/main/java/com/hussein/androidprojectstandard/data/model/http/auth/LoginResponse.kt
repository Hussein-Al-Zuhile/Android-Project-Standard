package com.hussein.androidprojectstandard.data.model.http.auth

import com.hussein.androidprojectstandard.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val user: User,
)