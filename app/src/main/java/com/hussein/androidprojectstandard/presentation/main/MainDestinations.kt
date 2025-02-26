package com.hussein.androidprojectstandard.presentation.main

import kotlinx.serialization.Serializable

sealed class MainDestinations {
    @Serializable
    data object Login : MainDestinations()

    @Serializable
    data object Home : MainDestinations()
}