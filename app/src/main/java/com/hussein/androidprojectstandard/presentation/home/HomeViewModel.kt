package com.hussein.androidprojectstandard.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hussein.androidprojectstandard.presentation.base.BaseViewModel

class HomeViewModel : BaseViewModel<HomeScreenStateEvent>() {

    var state by mutableStateOf(HomeScreenState())
        private set

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.OnLogoutClicked -> {
                _singleStateEventChannel.trySend(HomeScreenStateEvent.OnLogoutClicked)
            }
        }
    }
}