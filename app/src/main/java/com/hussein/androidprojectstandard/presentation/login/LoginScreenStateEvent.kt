package com.hussein.androidprojectstandard.presentation.login

import com.hussein.androidprojectstandard.presentation.base.StateEvent


sealed interface LoginScreenStateEvent :
    StateEvent {
    data class OnLoginSucceeded(val token: String) : LoginScreenStateEvent
}