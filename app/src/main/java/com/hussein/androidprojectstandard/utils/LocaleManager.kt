package com.hussein.androidprojectstandard.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleManager {

    val DefaultLanguage = Locale.ENGLISH

    fun getCurrentLocale(): Locale = AppCompatDelegate.getApplicationLocales()
        .takeIf { it != LocaleListCompat.getEmptyLocaleList() }?.get(0) ?: DefaultLanguage

    fun setCurrentLocale(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))
    }
}