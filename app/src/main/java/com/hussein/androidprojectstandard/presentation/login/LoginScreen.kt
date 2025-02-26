package com.hussein.androidprojectstandard.presentation.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hussein.androidprojectstandard.R
import com.hussein.androidprojectstandard.presentation.base.PreviewTablet
import com.hussein.androidprojectstandard.presentation.theme.AppTheme
import com.hussein.androidprojectstandard.presentation.theme.DoubleDefaultDp
import com.hussein.androidprojectstandard.presentation.theme.HalfDefaultDp
import com.hussein.androidprojectstandard.presentation.theme.ThreeQuarteredDefaultDp
import com.hussein.androidprojectstandard.presentation.theme.ThreeQuarteredDoubleDefaultSp
import com.hussein.androidprojectstandard.utils.LocaleManager
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun LoginScreen(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Row {
            val scrollState = rememberScrollState()
            Box(
                Modifier
                    .padding(DoubleDefaultDp)
                    .weight(0.8f)
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.sign_in_here),
                        fontSize = ThreeQuarteredDoubleDefaultSp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        stringResource(R.string.text_please_enter_your_email_and_password),
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(Modifier.height(HalfDefaultDp))
                    EmailTextField(state, onEvent)
                    Spacer(Modifier.height(HalfDefaultDp))
                    PasswordTextField(state, onEvent)
                    Spacer(Modifier.height(ThreeQuarteredDefaultDp))
                    Login(state, onEvent)
                    Spacer(Modifier.height(HalfDefaultDp))
                    ChangeLanguageButton(onEvent)
                }
                ScrollableContentIndicator(scrollState, Modifier.align(Alignment.BottomEnd))
            }
            Image(
                painterResource(R.drawable.bg_image_login_screen),
                contentDescription = null,
                Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
private fun ColumnScope.ChangeLanguageButton(onEvent: (LoginScreenEvent) -> Unit) {
    TextButton(
        onClick = {
            if (LocaleManager.getCurrentLocale() == Locale.ENGLISH) {
                onEvent(LoginScreenEvent.OnLanguageChanged(Locale("ar")))
            } else {
                onEvent(LoginScreenEvent.OnLanguageChanged(Locale.ENGLISH))
            }
        },
        Modifier.Companion.align(Alignment.Start)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_language),
            contentDescription = "Choose Language Icon",
            Modifier.size(30.dp)
        )
        Spacer(Modifier.width(HalfDefaultDp))
        Text(
            text = stringResource(R.string.label_for_changing_language_click_here),
            textDecoration = TextDecoration.Underline,
        )
    }
}

@Composable
private fun Login(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit
) {
    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        Button(
            onClick = { onEvent(LoginScreenEvent.OnSignInClicked) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                text = stringResource(R.string.label_sign_in).uppercase(),
                fontWeight = FontWeight.SemiBold,
                fontSize = ThreeQuarteredDoubleDefaultSp,
                modifier = Modifier.padding(vertical = HalfDefaultDp)
            )
        }
    }
}

@Composable
private fun PasswordTextField(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit
) {
    OutlinedTextField(
        state.password,
        onValueChange = { onEvent(LoginScreenEvent.OnPasswordChanged(it)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                stringResource(R.string.label_password),
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = ThreeQuarteredDoubleDefaultSp
            )
        },
        maxLines = 1,
        trailingIcon = {
            IconButton(
                onClick = {
                    onEvent(LoginScreenEvent.OnPasswordVisibilityToggled)
                }
            ) {
                if (state.isPasswordVisible) {
                    Icon(Icons.Default.VisibilityOff, "Hide Password")
                } else {
                    Icon(Icons.Default.Visibility, "See Password Button")
                }
            }
        },
        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
    )
}

@Composable
private fun EmailTextField(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit
) {
    OutlinedTextField(
        state.usernameOrEmail,
        onValueChange = { onEvent(LoginScreenEvent.OnMilitaryIDChanged(it)) },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                stringResource(R.string.label_username_or_email),
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = ThreeQuarteredDoubleDefaultSp
            )
        },
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
    )
}

@Composable
private fun ScrollableContentIndicator(
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    val isReachedBottom by remember { derivedStateOf { !scrollState.canScrollForward } }
    val coroutineScope = rememberCoroutineScope()
    AnimatedContent(
        !isReachedBottom, modifier,
    ) {
        if (it) {
            FilledTonalIconButton(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }) {
                Icon(Icons.Default.ArrowCircleDown, "Scroll Down")
            }
        }
    }
}

@PreviewTablet
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        var state by remember { mutableStateOf(LoginScreenState()) }
        LoginScreen(state, {
            when (it) {
                is LoginScreenEvent.OnMilitaryIDChanged -> {}
                is LoginScreenEvent.OnPasswordChanged -> {
                    state = state.copy(password = it.password)
                }

                else -> {}
            }
        })
    }
}
