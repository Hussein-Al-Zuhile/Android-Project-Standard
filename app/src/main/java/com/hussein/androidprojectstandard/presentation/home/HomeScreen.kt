package com.hussein.androidprojectstandard.presentation.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hussein.androidprojectstandard.R
import com.hussein.androidprojectstandard.presentation.base.DataStoreManager.tokenPreference
import com.hussein.androidprojectstandard.presentation.theme.AppTheme

@Composable
fun HomeScreen(
    state : HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Text("Home Screen")

    val context = LocalContext.current
    Button(onClick = {
        context.tokenPreference.removePreference()
    }) {
        Text(stringResource(R.string.label_logout))
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    AppTheme {
        HomeScreen(HomeScreenState(), {})
    }
}