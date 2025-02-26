package com.hussein.androidprojectstandard.presentation.base

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview


@Preview(device = "spec:width=1280dp,height=800dp,dpi=240", showBackground = true)
@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(device = "spec:width=1280dp,height=800dp,dpi=240", showBackground = true, locale = "ar")
@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=240",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    locale = "ar"
)
annotation class PreviewTablet

@Preview(name = "85%", fontScale = 0.85f, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "100%", fontScale = 1.0f, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "115%", fontScale = 1.15f, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "130%", fontScale = 1.3f, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "150%", fontScale = 1.5f, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "180%", fontScale = 1.8f, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "200%", fontScale = 2f, device = "spec:width=1280dp,height=800dp,dpi=240")
annotation class PreviewTablets
