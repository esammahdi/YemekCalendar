package com.esammahdi.yemekcalendar.foodItemList.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.core.data.models.FoodItem
import com.esammahdi.yemekcalendar.core.data.models.FoodType

@Composable
fun FoodItemTitle(
    foodItem: FoodItem,
    minTitleOffset: Dp,
    maxTitleOffset: Dp,
    titleHeight: Dp,
    hzPadding: Modifier,
    scrollProvider: () -> Int
) {
    val maxOffset = with(LocalDensity.current) { maxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { minTitleOffset.toPx() }

    val foodType = when (foodItem.foodType) {
        FoodType.MainCourse -> stringResource(R.string.main_course)
        FoodType.SecondaryCourse -> stringResource(R.string.second_course)
        FoodType.Soup -> stringResource(R.string.soup)
        FoodType.SideDish -> stringResource(R.string.side_dish)
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = titleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = foodItem.name,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = hzPadding
        )
        Text(
            text = foodType,
            style = MaterialTheme.typography.titleSmall,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = hzPadding
        )

        Spacer(Modifier.height(40.dp))

        HorizontalDivider(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
            thickness = 1.dp,
        )
    }
}