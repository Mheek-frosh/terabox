package com.example.terabox

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class TeraboxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val night = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(
            if (night) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    companion object {
        const val PREFS_NAME = "terabox_prefs"
        const val KEY_NIGHT_MODE = "night_mode"
        const val KEY_AD_VOICE = "ad_voice"
    }
}
