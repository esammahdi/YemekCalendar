package com.example.yemekcalendar.authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.yemekcalendar.ui.theme.RegularFont

@Composable
fun Redirection(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = modifier
                .clickable { onClick() },
            text = text,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            fontFamily = RegularFont
        )
    }
}