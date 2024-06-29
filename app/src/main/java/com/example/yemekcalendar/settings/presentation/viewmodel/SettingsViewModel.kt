package com.example.yemekcalendar.settings.presentation.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcalendar.core.data.interfaces.LocalUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository
) : ViewModel() {

    val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    var initializeCalled = false

    // This function is idempotent provided it is only called from the UI thread.
    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true

        viewModelScope.launch {
            loadSettings()
        }
    }

    private suspend fun loadSettings() {
        val isUserRemembered = localUserRepository.isUserSaved()
        val userEmail = getUserEmail(isUserRemembered)

        _settingsState.value = _settingsState.value.copy(
            email = userEmail,
        )
    }

    private suspend fun getUserEmail(isUserRemembered: Boolean): String {
        return if (isUserRemembered) {
            localUserRepository.getSavedUser()
        } else {
            localUserRepository.getTemporaryUser()
        }
    }

}

data class SettingsState(
    val email: String = "",
    val profilePictureUrl: String = "",
    val isError: Boolean = false,
    val isLoggoedOut: Boolean = false
)
