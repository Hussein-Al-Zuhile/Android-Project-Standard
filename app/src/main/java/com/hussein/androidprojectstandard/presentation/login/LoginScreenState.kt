package com.hussein.androidprojectstandard.presentation.login

import com.hussein.androidprojectstandard.presentation.base.BaseUIState
import com.hussein.androidprojectstandard.utils.UIText

data class LoginScreenState(
    val usernameOrEmail: String = "yard1@gmail.com",
    val password: String = "User@123",
    val isPasswordVisible: Boolean = false,
    val isSignInEnabled: Boolean = false,
    val isCardSignInEnabled: Boolean = false,
    override val isLoading: Boolean = false,
    override val errorMessage: UIText? = null
) : BaseUIState()
