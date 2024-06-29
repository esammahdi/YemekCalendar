package com.example.yemekcalendar.foodItemList.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.presentation.components.ErrorScreen
import com.example.yemekcalendar.core.presentation.components.LoadingScreen
import com.example.yemekcalendar.core.presentation.components.YemekCalendarDropdownList
import com.example.yemekcalendar.foodItemList.presentation.components.FoodItemTitle
import com.example.yemekcalendar.foodItemList.presentation.screens.components.FoodItemBody
import com.example.yemekcalendar.foodItemList.presentation.screens.components.FoodItemHeader
import com.example.yemekcalendar.foodItemList.presentation.screens.components.FoodItemImage
import com.example.yemekcalendar.foodItemList.presentation.viewmodel.FoodDetailsViewModel
import com.example.yemekcalendar.ui.theme.YemekCalendarTheme


private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun FoodDetailsScreen(
    foodItemName: String,
    navController: NavController,
    foodItemsViewModel: FoodDetailsViewModel = hiltViewModel(),
) {

    foodItemsViewModel.initialize(foodItemName)
    val foodItemDetailsState by foodItemsViewModel.foodItemDetailsState.collectAsStateWithLifecycle()

    when {
        foodItemDetailsState.isLoading -> LoadingScreen()
        foodItemDetailsState.isError -> ErrorScreen(foodItemDetailsState.errorMessage)
        else -> {
            val foodItem = foodItemDetailsState.foodItem

            Box(Modifier.fillMaxSize()) {
                val scroll = rememberScrollState(0)

                FoodItemHeader()

                FoodItemBody(
                    foodItemDetailsState = foodItemDetailsState,
                    foodItemsViewModel = foodItemsViewModel,
                    minTitleOffset = MinTitleOffset,
                    imageOverlap = ImageOverlap,
                    gradientScroll = GradientScroll,
                    titleHeight = TitleHeight,
                    hzPadding = HzPadding,
                    scroll = scroll,
                )

                FoodItemTitle(
                    foodItem = foodItem,
                    minTitleOffset = MinTitleOffset,
                    maxTitleOffset = MaxTitleOffset,
                    titleHeight = TitleHeight,
                    hzPadding = HzPadding
                ) {
                    scroll.value
                }

                FoodItemImage(
                    imageUrl = foodItem.imageUrl,
                    itemName = foodItem.name,
                    minTitleOffset = MinTitleOffset,
                    maxTitleOffset = MaxTitleOffset,
                    hzPadding = HzPadding,
                ) {
                    scroll.value
                }

                // Make a function to navigate back up in the stack
                BackButton {
                    navController.navigateUp()
                }
            }
        }
    }
}


@Composable
private fun BackButton(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
fun InstitutionDropdown(
    institutionList: List<String>,
    selectedInstitution: String,
    onInstitutionSelected: (String) -> Unit
) {
    YemekCalendarDropdownList(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        wrapContentWidth = false,
        itemList = institutionList,
        selectedItemText = selectedInstitution,
        onSelectedIndexChanged = { onInstitutionSelected(it) },
    )
}


@Preview
@Composable
private fun FoodItemDetailsPreview() {
    YemekCalendarTheme {
//        FoodItemScreen(
//
//        )
    }
}

