package com.esammahdi.yemekcalendar.core.data.repositories

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.esammahdi.yemekcalendar.core.data.interfaces.LocalUserRepository
import com.esammahdi.yemekcalendar.settings.presentation.viewmodel.Languages
import com.esammahdi.yemekcalendar.settings.presentation.viewmodel.ThemeMode
import com.esammahdi.yemekcalendar.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val appContext: Context,
    private val dataStore: DataStore<Preferences>
) : LocalUserRepository {
    override suspend fun saveTemporaryUser(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[TEMPORARY_USER_EMAIL] = userEmail
        }
    }

    override suspend fun saveUser(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[SAVED_USER_EMAIL] = userEmail
        }
    }

    override suspend fun saveProfilePictureDownloadUrl(url: String, email: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(PROFILE_PICTURE_DOWNLOAD_URL + email)] = url
        }
    }

    override suspend fun getProfilePictureDownloadUrl(email: String): String? {
        val preferences = dataStore.data.first()
        return preferences[stringPreferencesKey(PROFILE_PICTURE_DOWNLOAD_URL + email)]
    }

    override suspend fun getTemporaryUser(): String {
        val preferences = dataStore.data.first()
        return preferences[TEMPORARY_USER_EMAIL] ?: "No Email Found"
    }

    override suspend fun getSavedUser(): String {
        val preferences = dataStore.data.first()
        return preferences[SAVED_USER_EMAIL] ?: "No Email Found"
    }

    override suspend fun deleteSavedUser() {
        dataStore.edit { preferences ->
            preferences.remove(SAVED_USER_EMAIL)
        }
    }

    override suspend fun isUserSaved(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[SAVED_USER_EMAIL] != null
    }

    // =============================== Theme ================================================================
    override suspend fun saveThemeChoice(appTheme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[APP_THEME] = appTheme.name
        }
    }

    override suspend fun getThemeChoice(): AppTheme {
        val themeName = dataStore.data.first()[APP_THEME]
        return AppTheme.valueOf(themeName ?: AppTheme.DEFAULT.name)
    }

    override fun getThemeChoiceFlow(): Flow<AppTheme> {
        return dataStore.data.map { preferences ->
            val themeName = preferences[APP_THEME] ?: AppTheme.DEFAULT.name
            AppTheme.valueOf(themeName)
        }
    }
    // =================================== Theme Mode ==========================================================
    override suspend fun getThemeMode(): ThemeMode {
        val themeMode = dataStore.data.first()[THEME_MODE]
        return ThemeMode.fromString(themeMode) ?: ThemeMode.LIGHT
    }

    override suspend fun getThemeModeFlow(): Flow<ThemeMode> {
        return dataStore.data.map { preferences ->
            val themeMode = preferences[THEME_MODE] ?: ThemeMode.LIGHT.name
            ThemeMode.valueOf(themeMode)
        }
    }

    override suspend fun saveThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.name
        }
    }
    // ====================================== Dynamic Color =================================================================

    override suspend fun saveDynamicColor(isDynamicColor: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DYNAMIC_COLOR] = isDynamicColor
        }
    }

    override suspend fun getDynamicColor(): Boolean {
        return dataStore.data.first()[IS_DYNAMIC_COLOR] ?: false
    }

    override suspend fun getDynamicColorFlow(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_DYNAMIC_COLOR] ?: false
        }
    }

    // ====================================== Language =================================================================
    override suspend fun saveLanguageChoice(languageChoice: Languages) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = languageChoice.languageCode
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Use LocaleManager for Android 12 and later
            appContext.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageChoice.languageCode)
        } else {
            // For earlier versions, update the app's configuration manually
            val locale = Locale(languageChoice.languageCode)
            Locale.setDefault(locale)

            val resources = appContext.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }

    override suspend fun getLanguageChoice(): Languages {
        val languageCode = dataStore.data.first()[LANGUAGE]
        return Languages.fromLanguageCode(languageCode) ?: Languages.ENGLISH
    }

    // ===================================== Companion Objects ==========================================================
    companion object {
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val IS_DYNAMIC_COLOR = booleanPreferencesKey("is_dynamic_color")
        private val APP_THEME = stringPreferencesKey("app_theme")
        private val LANGUAGE = stringPreferencesKey("language")
        private val TEMPORARY_USER_EMAIL = stringPreferencesKey("temporary_user_email")
        private val SAVED_USER_EMAIL = stringPreferencesKey("saved_user_email")
        private val PROFILE_PICTURE_DOWNLOAD_URL = "profile_picture_url"
    }

}
