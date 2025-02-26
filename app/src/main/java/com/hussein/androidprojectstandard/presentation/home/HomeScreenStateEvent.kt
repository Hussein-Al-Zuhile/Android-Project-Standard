package com.hussein.androidprojectstandard.presentation.home

import com.hussein.androidprojectstandard.presentation.base.StateEvent


sealed interface HomeScreenStateEvent : StateEvent {
    data object OnLogoutClicked : HomeScreenStateEvent
}