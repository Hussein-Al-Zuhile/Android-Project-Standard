package com.hussein.androidprojectstandard.presentation.base

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hussein.androidprojectstandard.presentation.theme.AppTheme
import com.hussein.androidprojectstandard.utils.MessageType
import com.hussein.androidprojectstandard.utils.UIText



@Composable
fun MessageSnackbar(
    snackbarHostState: SnackbarHostState,
    message: UIText?,
    modifier: Modifier = Modifier
) {

    val containerColor =
        when (message?.type) {
            MessageType.Success -> MaterialTheme.colorScheme.primaryContainer
            MessageType.Error -> MaterialTheme.colorScheme.errorContainer
            MessageType.Warning -> MaterialTheme.colorScheme.surfaceContainerHigh
            MessageType.Info -> MaterialTheme.colorScheme.surface
            null -> SnackbarDefaults.color
        }


    SnackbarHost(snackbarHostState, modifier) {
        Snackbar(
            it,
            containerColor = containerColor,
            contentColor = contentColorFor(containerColor)
        )
    }
}

@Preview
@Composable
private fun PreviewMessageSnackbar() {
    AppTheme {
        MessageSnackbar(remember { SnackbarHostState() }, message = UIText.Empty)
    }
}