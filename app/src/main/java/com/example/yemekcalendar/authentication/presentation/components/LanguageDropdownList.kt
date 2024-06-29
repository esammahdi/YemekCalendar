package com.example.yemekcalendar.authentication.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yemekcalendar.core.presentation.components.YemekCalendarDropdownList
import com.example.yemekcalendar.settings.presentation.viewmodel.Languages

@Composable
fun LanguageDropdownList(
    selectedLanguage: Languages,
    onLanguageSelected: (Languages) -> Unit
) {
    val availableLanguages = Languages.entries.map { it.name }
    val language = stringResource(selectedLanguage.languageStringResourceId)

    YemekCalendarDropdownList(
        itemList = availableLanguages,
        selectedItemText = language,
        onSelectedIndexChanged = { selectedLang ->
            onLanguageSelected(Languages.valueOf(selectedLang))
        }
    )
}
