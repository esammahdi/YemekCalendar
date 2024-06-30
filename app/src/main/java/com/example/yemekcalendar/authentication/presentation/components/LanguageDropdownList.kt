package com.example.yemekcalendar.authentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.presentation.components.YemekCalendarDropdownList
import com.example.yemekcalendar.settings.presentation.viewmodel.Languages

@Composable
fun LanguageDropdownList(
    selectedLanguage: Languages,
    onLanguageSelected: (Languages) -> Unit
) {
    val availableLanguages = Languages.entries.map { it.name }
    val language = stringResource(selectedLanguage.languageStringResourceId)
    val languageIcon = ImageVector.vectorResource(id = R.drawable.outline_language_24)

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(38.dp),
            imageVector = languageIcon,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Language Icon"
        )

        Spacer(modifier = Modifier.width(8.dp))

        YemekCalendarDropdownList(
            itemList = availableLanguages,
            selectedItemText = language,
            onSelectedIndexChanged = { selectedLang ->
                onLanguageSelected(Languages.valueOf(selectedLang))
            }
        )
    }
}
