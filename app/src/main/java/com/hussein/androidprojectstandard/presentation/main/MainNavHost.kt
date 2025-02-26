package com.hussein.androidprojectstandard.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.hussein.androidprojectstandard.R
import com.hussein.androidprojectstandard.presentation.base.DataStoreManager.tokenPreference
import com.hussein.androidprojectstandard.presentation.login.LoginScreen
import com.hussein.androidprojectstandard.presentation.login.LoginScreenStateEvent
import com.hussein.androidprojectstandard.presentation.login.LoginViewModel
import com.hussein.androidprojectstandard.presentation.theme.AppTheme
import com.hussein.androidprojectstandard.utils.ConsumeEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController, MainDestinations.Login, modifier) {

        composable<MainDestinations.Login> {

            LaunchedEffect(Unit) {
                if (context.tokenPreference.preferenceValueAsObject() != null) {
                    navController.navigate(
                        MainDestinations.Home,
                        navOptions {
                            popUpTo<MainDestinations.Login> { inclusive = true }
                            launchSingleTop = true
                        }
                    )
                }
            }


            val viewModel = koinViewModel<LoginViewModel>()

            viewModel.singleStateEventChannel.ConsumeEach {
                when (it) {
                    is LoginScreenStateEvent.OnLoginSucceeded -> {
                        with(context) {
                            tokenPreference.editPreferenceAsObject(it.token)
                        }
                        navController.navigate(MainDestinations.Home)
                    }
                }
            }

            LoginScreen(viewModel.loginScreenState, viewModel::onEvent)
        }

        composable<MainDestinations.Home> {
            println(navController.currentBackStackEntryAsState().value)
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Home Screen")

                Button(onClick = {
                    with(context) {
                        tokenPreference.removePreference()
                        navController.navigate(
                            MainDestinations.Login,
                            navOptions {
                                popUpTo<MainDestinations.Home> { inclusive = true }
                                launchSingleTop = true
                            }
                        )
                    }
                }) {
                    Text(stringResource(R.string.label_logout))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainNavHost() {
    AppTheme {
        MainNavHost()
    }
}