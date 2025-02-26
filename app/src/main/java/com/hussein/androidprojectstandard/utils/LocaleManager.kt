package com.hussein.androidprojectstandard.utils

import android.annotation.SuppressLint
import android.app.LocaleConfig
import android.app.LocaleManager
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.LocaleManagerCompat
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleManager {

    val DefaultLanguage = Locale.ENGLISH

    fun getCurrentLanguage(): Locale = AppCompatDelegate.getApplicationLocales()
        .takeIf { it != LocaleListCompat.getEmptyLocaleList() }?.get(0) ?: DefaultLanguage

    fun setCurrentLanguage(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))
    }
}