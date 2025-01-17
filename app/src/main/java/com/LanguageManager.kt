package com

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LanguageManager {

    fun changeLanguage(context: Context) {
        val config = context.resources.configuration
        val currentLanguage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales[0].language
        } else {
            @Suppress("DEPRECATION")
            config.locale.language
        }
        if (currentLanguage == "en") {
            setLocale(context, "fr")
            return
        }
        if (currentLanguage == "fr") {
            setLocale(context, "en")
            return
        }
    }

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocales(android.os.LocaleList(locale))
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
