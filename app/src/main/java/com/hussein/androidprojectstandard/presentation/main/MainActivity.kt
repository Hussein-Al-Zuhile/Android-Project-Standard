package com.hussein.androidprojectstandard.presentation.main

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

import com.hussein.androidprojectstandard.presentation.base.MessageSnackbar
import com.hussein.androidprojectstandard.utils.UIText
import com.hussein.androidprojectstandard.presentation.theme.AppTheme
import com.hussein.androidprojectstandard.utils.ConsumeEach

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope?> { null }

val EventMessageChannel = Channel<UIText>(10)

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            KoinContext {
                AppTheme {

                    // Observe event messages.
                    val snackbarHostState = remember { SnackbarHostState() }
                    var messageState by remember { mutableStateOf<UIText?>(null) }
                    EventMessageChannel.ConsumeEach { message ->
                        messageState = message
                        snackbarHostState.showSnackbar(
                            message.asString(this),
                            duration = message.duration
                        )
                    }
                    SharedTransitionLayout {
                        CompositionLocalProvider(LocalSharedTransitionScope provides this@SharedTransitionLayout) {
                            Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                snackbarHost = {
                                    MessageSnackbar(snackbarHostState, message = messageState)
                                }
                            ) { innerPadding ->

                            }
                        }
                    }
                }
            }
        }
    }

}