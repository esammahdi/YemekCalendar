package com.esammahdi.yemekcalendar.calendar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.esammahdi.yemekcalendar.calendar.presentation.Months

@Composable
fun MonthNavigationBar(
    modifier: Modifier = Modifier,
    month: Months,
    onBackArrowClicked: () -> Unit,
    onForwardArrowClicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = {
            onBackArrowClicked()
        }) {
            Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Previous Month")
        }
        Text(
            text = month.name,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
        IconButton(onClick = {
            onForwardArrowClicked()
        }) {
            Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = "Next Month")
        }
    }
}