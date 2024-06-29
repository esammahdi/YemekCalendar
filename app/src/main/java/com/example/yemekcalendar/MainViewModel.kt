package com.example.yemekcalendar

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcalendar.core.data.interfaces.LocalUserRepository
import com.example.yemekcalendar.settings.presentation.viewmodel.ThemeMode
import com.example.yemekcalendar.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository
) : ViewModel() {
    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true

        viewModelScope.launch {
            val isUserSignedIn = localUserRepository.isUserSaved()
            _mainState.value = _mainState.value.copy(
                isUserSignedIn = isUserSignedIn
            )
            // Start observing all flows concurrently
            combine(
                localUserRepository.getThemeChoiceFlow(),
                localUserRepository.getThemeModeFlow(),
                localUserRepository.getDynamicColorFlow()
            ) { theme, themeMode, isDynamicColor ->
                MainState(
                    theme = theme,
                    themeMode = themeMode,
                    isDynamicColor = isDynamicColor,
                    isUserSignedIn = _mainState.value.isUserSignedIn // Retain existing value
                )
            }.collect { newState ->
                _mainState.value = newState
            }
        }
    }
}

data class MainState(
    val theme: AppTheme = AppTheme.DEFAULT,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val isDynamicColor: Boolean = false,
    val isUserSignedIn: Boolean = false
)