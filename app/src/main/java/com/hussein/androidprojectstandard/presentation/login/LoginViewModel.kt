package com.hussein.androidprojectstandard.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hussein.androidprojectstandard.domain.base.Result
import com.hussein.androidprojectstandard.domain.usecase.http.auth.LoginUseCase
import com.hussein.androidprojectstandard.presentation.base.BaseViewModel
import com.hussein.androidprojectstandard.presentation.main.EventMessageChannel
import com.hussein.androidprojectstandard.utils.LocaleManager

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginScreenStateEvent>() {

    var loginScreenState by mutableStateOf(LoginScreenState())
        private set

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnMilitaryIDChanged -> {
                loginScreenState = loginScreenState.copy(usernameOrEmail = event.militaryID)
            }

            is LoginScreenEvent.OnPasswordChanged -> {
                loginScreenState = loginScreenState.copy(password = event.password)
            }

            is LoginScreenEvent.OnPasswordVisibilityToggled -> {
                loginScreenState =
                    loginScreenState.copy(isPasswordVisible = !loginScreenState.isPasswordVisible)
            }

            is LoginScreenEvent.OnSignInClicked -> {
                loginUseCase(
                    loginScreenState.usernameOrEmail,
                    loginScreenState.password
                ).collectInViewModelScope { result ->
                    when (result) {
                        is Result.Loading -> {
                            loginScreenState = loginScreenState.copy(isLoading = true)
                        }

                        is Result.Success.Data -> {
                            loginScreenState = loginScreenState.copy(isLoading = false)
                            _singleStateEventChannel.send(
                                LoginScreenStateEvent.OnLoginSucceeded(
                                    result.data.accessToken
                                )
                            )
                        }

                        is Result.Failure -> {
                            loginScreenState = loginScreenState.copy(isLoading = false)
                            EventMessageChannel.send(result.message)
                        }

                        else -> {}
                    }
                }
            }

            is LoginScreenEvent.OnCardSignInDetected -> {
                // Handle card sign-in logic
            }

            is LoginScreenEvent.OnLanguageChanged -> {
                LocaleManager.setCurrentLocale(event.locale)
            }
        }
    }
}