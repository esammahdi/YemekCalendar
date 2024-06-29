package com.example.yemekcalendar.authentication.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.yemekcalendar.ui.theme.RegularFont

class Description

@Composable
fun Description(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        fontFamily = RegularFont
    )
}