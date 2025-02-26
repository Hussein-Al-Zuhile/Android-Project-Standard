package com.hussein.androidprojectstandard.presentation.login

import java.util.Locale

sealed interface LoginScreenEvent {
    data class OnMilitaryIDChanged(val militaryID: String) : LoginScreenEvent
    data class OnPasswordChanged(val password: String) : LoginScreenEvent
    data object OnPasswordVisibilityToggled : LoginScreenEvent
    data object OnSignInClicked : LoginScreenEvent
    data object OnCardSignInDetected : LoginScreenEvent
    data class OnLanguageChanged(val locale: Locale) : LoginScreenEvent
}