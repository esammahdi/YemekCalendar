package com.example.yemekcalendar.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.yemekcalendar.ui.theme.RegularFont

@Composable
fun YemekCalendarDropdownList(
    modifier: Modifier = Modifier,
    wrapContentWidth: Boolean = true,
    itemList: List<String>,
    selectedItemText: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onSelectedIndexChanged: (String) -> Unit = {},
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .padding(5.dp)
                .let{
                    if (wrapContentWidth) {
                        it.wrapContentWidth()
                    } else {
                        it.fillMaxWidth()
                    }
                }
                .clickable {
                    expanded = true
                }
        ) {
            Text(
                text = selectedItemText,
                modifier = Modifier.padding(horizontal = 4.dp),
                fontFamily = RegularFont,
                style = textStyle,
                color = textColor
            )
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "ArrowDropDown"
            )
        }

        DropdownMenu(
            modifier = modifier.let {
                if (wrapContentWidth) {
                    it.wrapContentWidth()
                } else {
                    it.fillMaxWidth()
                }
            },
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            itemList.forEachIndexed { index, strings ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = strings,
                            color = textColor
                        )
                    },
                    onClick = {
                        expanded = false
                        selectedIndex = index
                        onSelectedIndexChanged(itemList[selectedIndex])
                    }
                )
            }

        }
    }
}