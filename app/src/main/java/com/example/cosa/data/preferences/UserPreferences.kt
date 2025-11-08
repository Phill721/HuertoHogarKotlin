package com.example.cosa.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPrefsKeys {
    val EMAIL = stringPreferencesKey("email")
}