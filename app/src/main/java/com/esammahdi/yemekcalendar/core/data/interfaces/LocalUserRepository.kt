package com.esammahdi.yemekcalendar.core.data.interfaces

import com.esammahdi.yemekcalendar.settings.presentation.viewmodel.Languages
import com.esammahdi.yemekcalendar.settings.presentation.viewmodel.ThemeMode
import com.esammahdi.yemekcalendar.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {
    //==============================================================================================
    suspend fun saveTemporaryUser(userEmail: String)
    suspend fun getTemporaryUser(): String
    suspend fun saveUser(userEmail: String)
    suspend fun getSavedUser(): String
    suspend fun deleteSavedUser()
    suspend fun isUserSaved(): Boolean

    //==============================================================================================
    suspend fun saveProfilePictureDownloadUrl(url: String, email: String)
    suspend fun getProfilePictureDownloadUrl(email: String): String?

    //==============================================================================================
    suspend fun saveLanguageChoice(languageChoice: Languages)
    suspend fun getLanguageChoice(): Languages

    //==============================================================================================
    suspend fun saveThemeChoice(appTheme: AppTheme)
    suspend fun getThemeChoice(): AppTheme
    fun getThemeChoiceFlow(): Flow<AppTheme>

    //==============================================================================================
    suspend fun saveThemeMode(themeMode: ThemeMode)
    suspend fun getThemeMode(): ThemeMode
    suspend fun getThemeModeFlow(): Flow<ThemeMode>

    //==============================================================================================
    suspend fun getDynamicColorFlow(): Flow<Boolean>
    suspend fun getDynamicColor(): Boolean
    suspend fun saveDynamicColor(isDynamicColor: Boolean)
}