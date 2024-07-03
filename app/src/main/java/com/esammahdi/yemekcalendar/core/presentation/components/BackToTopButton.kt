package com.esammahdi.yemekcalendar.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.esammahdi.yemekcalendar.R

@Composable
fun BackToTopButton(
    goToTop: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier
            .padding(16.dp)
            .size(50.dp),
//            .align(Alignment.BottomEnd),
        onClick = goToTop,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_arrow_upward_24),
            contentDescription = "go to top",
        )
    }

}