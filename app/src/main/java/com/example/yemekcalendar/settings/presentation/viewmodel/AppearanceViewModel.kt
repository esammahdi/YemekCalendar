package com.example.yemekcalendar.settings.presentation.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.data.interfaces.LocalUserRepository
import com.example.yemekcalendar.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository
) : ViewModel() {

    private val _appearanceState = MutableStateFlow(AppearanceState())
    val appearanceState = _appearanceState.asStateFlow()
    private var initializeCalled = false

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
        val languageChoice = getLanguageChoice()
        val themeChoice = getThemeChoice()
        val themeMode = getThemeMode()
        val isDynamicColor = getDynamicColor()

        _appearanceState.value = _appearanceState.value.copy(
            selectedLanguage = languageChoice,
            selectedTheme = themeChoice,
            selectedThemeMode = themeMode,
            isDynamicColor = isDynamicColor
        )
    }


    //==============================================================================================
    private suspend fun getThemeChoice(): AppTheme {
        return localUserRepository.getThemeChoiceFlow().first()
    }

    fun onThemeSelected(theme: AppTheme) = viewModelScope.launch {
        localUserRepository.saveThemeChoice(theme)
        _appearanceState.value = _appearanceState.value.copy(selectedTheme = theme)
    }

    //==============================================================================================
    suspend fun getThemeMode(): ThemeMode {
        return localUserRepository.getThemeMode()
    }

    fun onThemeModeSelected(themeMode: ThemeMode) = viewModelScope.launch {
        localUserRepository.saveThemeMode(themeMode)
        _appearanceState.value = _appearanceState.value.copy(selectedThemeMode = themeMode)
    }

    //==============================================================================================
    suspend fun getDynamicColor(): Boolean {
        return localUserRepository.getDynamicColor()
    }

    fun onDynamicColorSelected(isDynamicColor: Boolean) = viewModelScope.launch {
        localUserRepository.saveDynamicColor(isDynamicColor)
        _appearanceState.value = _appearanceState.value.copy(isDynamicColor = isDynamicColor)
    }

    //==============================================================================================
    private suspend fun getLanguageChoice(): Languages {
        return localUserRepository.getLanguageChoice()
    }

    fun onLanguageSelected(language: Languages) = viewModelScope.launch {
        localUserRepository.saveLanguageChoice(language)
        _appearanceState.value = _appearanceState.value.copy(selectedLanguage = language)
    }

}

//==================================================================================================

enum class Languages(val languageStringResourceId: Int, val languageCode: String) {
    ENGLISH(R.string.english, "en"),
    TURKISH(R.string.turkish, "tr"),
    ARABIC(R.string.arabic, "ar");

    companion object {
        fun fromLanguageCode(code: String?): Languages? {
            return entries.find { it.languageCode == code }
        }
    }
}

enum class ThemeMode(val themeResId: Int) {
    SYSTEM(R.string.system),
    LIGHT(R.string.light),
    DARK(R.string.dark);

    companion object {
        fun fromString(mode: String?): ThemeMode? {
            return when (mode) {
                SYSTEM.name -> SYSTEM
                LIGHT.name -> LIGHT
                DARK.name -> DARK
                else -> null
            }
        }
    }
}


//==================================================================================================

data class AppearanceState(
    val selectedThemeMode: ThemeMode = ThemeMode.SYSTEM,
    val selectedTheme: AppTheme = AppTheme.DEFAULT,
    val selectedLanguage: Languages = Languages.ENGLISH,
    val isDynamicColor: Boolean = false,
)
