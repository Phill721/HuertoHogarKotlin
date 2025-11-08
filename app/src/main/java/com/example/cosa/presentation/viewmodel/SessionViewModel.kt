package com.example.cosa.presentation.viewmodel


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosa.data.preferences.UserPrefsKeys
import com.example.cosa.data.preferences.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SessionViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail

    fun login(email: String) {
        viewModelScope.launch {
            _userEmail.value = email
            _isLoggedIn.value = true
        }
    }

    fun saveSession(context: Context, email: String) {
        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                prefs[UserPrefsKeys.EMAIL] = email
            }
            _userEmail.value = email
            _isLoggedIn.value = true
        }
    }

    fun loadSession(context: Context) {
        viewModelScope.launch {
            context.dataStore.data.collectLatest { prefs ->
                val email = prefs[UserPrefsKeys.EMAIL]
                if (email != null) {
                    _userEmail.value = email
                    _isLoggedIn.value = true
                }
            }
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                prefs.remove(UserPrefsKeys.EMAIL)
            }
            _userEmail.value = null
            _isLoggedIn.value = false
        }
    }
}
