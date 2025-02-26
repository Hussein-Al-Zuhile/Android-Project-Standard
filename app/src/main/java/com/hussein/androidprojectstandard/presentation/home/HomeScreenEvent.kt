package com.hussein.androidprojectstandard.presentation.home

sealed interface HomeScreenEvent {
    data object OnLogoutClicked : HomeScreenEvent
}