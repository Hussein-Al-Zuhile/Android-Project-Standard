package com.hussein.androidprojectstandard.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<StateEventType : StateEvent> : ViewModel() {

    protected suspend fun <T> Flow<T>.asStateFlowInViewModelScope() = stateIn(viewModelScope)

    /**
     * State Events are a one-time event that is handled only once (Navigation, Toast, SnackBar, etc.)
     * and it's sent by the View Model, and consumed by the UI.
     */
    protected val _singleStateEventChannel = Channel<StateEventType>(capacity = 5)
    val singleStateEventChannel: ReceiveChannel<StateEventType> = _singleStateEventChannel

    protected fun <T> SendChannel<T>.sendInViewModelScope(stateEvent: T) {
        viewModelScope.launch {
            send(stateEvent)
        }
    }

    protected fun <T> Flow<T>.collectInViewModelScope(action: suspend (value: T) -> Unit) {
        viewModelScope.launch {
            collect(action)
        }
    }

}