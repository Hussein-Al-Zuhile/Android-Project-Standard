package com.hussein.androidprojectstandard.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UIText {

    val type: MessageType
    val duration: SnackbarDuration
        get() = SnackbarDuration.Short

    data class Hardcoded(
        val value: String,
        override val type: MessageType = MessageType.Info,
        override val duration: SnackbarDuration = SnackbarDuration.Short
    ) :
        UIText

    data class LocalizedString(
        @StringRes val resId: Int, val args: List<Any> = emptyList(),
        override val type: MessageType = MessageType.Info,
        override val duration: SnackbarDuration = SnackbarDuration.Short
    ) : UIText

    companion object {
        val Empty = Hardcoded("")
    }

    fun asString(context: Context) = when (this) {
        is Hardcoded -> value
        is LocalizedString -> context.getString(resId, *args.toTypedArray())
    }

    @Composable
    fun asString() = when (this) {
        is Hardcoded -> value
        is LocalizedString -> stringResource(resId, *args.toTypedArray())
    }
}

enum class MessageType {
    Success,
    Error,
    Warning,
    Info
}