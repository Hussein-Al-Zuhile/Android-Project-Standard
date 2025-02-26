package com.hussein.androidprojectstandard.data.model.http.auth

import com.hussein.androidprojectstandard.utils.LocaleManager
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest @OptIn(ExperimentalSerializationApi::class) constructor(
    val usernameOrEmailAddress: String,
    val password: String,
    @EncodeDefault val rememberClient: Boolean = true,
    @EncodeDefault val languageCode: String = LocaleManager.getCurrentLocale().language
)
