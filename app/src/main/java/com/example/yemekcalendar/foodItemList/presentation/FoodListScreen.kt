package com.example.yemekcalendar.foodItemList.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.data.models.FoodItem
import com.example.yemekcalendar.core.data.models.FoodType
import com.example.yemekcalendar.core.other.navigation.Screen
import com.example.yemekcalendar.core.presentation.components.BackToTopButton
import com.example.yemekcalendar.core.presentation.components.ErrorScreen
import com.example.yemekcalendar.core.presentation.components.Header
import com.example.yemekcalendar.core.presentation.components.LoadingScreen
import com.example.yemekcalendar.core.presentation.components.NoDataScreen
import com.example.yemekcalendar.core.presentation.components.Title
import com.example.yemekcalendar.core.presentation.components.YemekCalendarDropdownList
import com.example.yemekcalendar.foodItemList.presentation.viewmodel.FoodListViewModel
import com.example.yemekcalendar.ui.theme.RegularFont
import kotlinx.coroutines.launch
import kotlin.math.abs


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(
    navController: NavController,
    foodItemListViewModel: FoodListViewModel = hiltViewModel(),
) {
    foodItemListViewModel.initialize()
    val foodItemsState by foodItemListViewModel.foodItemsState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = foodItemsState.isRefreshing,
        onRefresh = { foodItemListViewModel.onRefresh() },
        modifier = Modifier,
        state = pullRefreshState
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Title(
                    text = stringResource(R.string.foods),
                    modifier = Modifier.padding(16.dp)
                )
            }

            when {
                foodItemsState.isLoading -> LoadingScreen()
                foodItemsState.isError -> ErrorScreen(foodItemsState.errorMessage)
                foodItemsState.foodItems.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NoDataScreen()
                    }
                }

                else -> {
                    val foodItems = foodItemsState.foodItems
                    var selectedCategoryItems by remember { mutableStateOf(foodItemsState.foodItems) }
                    val groupedFoodItems =
                        selectedCategoryItems.groupBy { it.name.first().uppercaseChar() }
                            .toSortedMap()
                    val letters = groupedFoodItems.keys.toList()
                    val listState = rememberLazyListState()
                    val scope = rememberCoroutineScope()
                    var selectedLetter by remember { mutableStateOf(letters.first()) }
                    val showBackToTopButton by remember {
                        derivedStateOf {
                            listState.firstVisibleItemIndex > 0
                        }
                    }


                    // Alphabetical Scroll Component
                    AlphabeticalScrollComponent(
                        letters = letters,
                        selectedLetter = selectedLetter,
                        onLetterSelected = { letter ->
                            selectedLetter = letter
                            scope.launch {
                                listState.animateScrollToItem(
                                    foodItems.indexOfFirst {
                                        it.name.first().uppercaseChar() == letter
                                    } + letters.indexOf(letter)
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.category),
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = RegularFont,
                        )

                        YemekCalendarDropdownList(
                            modifier = Modifier.weight(1f),
                            itemList = foodItemsState.categoryList,
                            selectedItemText = foodItemsState.selectedCategory,
                            onSelectedIndexChanged = {
                                selectedCategoryItems = filterItemsByCategory(
                                    foodItemsState.foodItems,
                                    it,
                                    foodItemsState.categoryList
                                )
                                foodItemListViewModel.onSelectedCategoryChanged(
                                    it
                                )
                            }
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (selectedCategoryItems.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.no_items_in_category),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge,
                            )

                            Spacer(modifier = Modifier.height(1.dp))

                            Text(
                                text = stringResource(R.string.no_item_in_category_message),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    } else {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f)
                        )


                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            // Main Body (LazyColumn)
                            LazyColumn(
                                state = listState,
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 0.dp
                                )
                            ) {
                                groupedFoodItems.forEach { (letter, items) ->
                                    item {
                                        Header(
                                            text = letter.toString(),
                                            modifier = Modifier.padding(vertical = 8.dp)
                                        )
                                    }

                                    items(items) { foodItem ->
                                        FoodItemCard(
                                            foodItem,
                                            onClick = {
                                                navController.navigate(
                                                    Screen.FoodDetailsScreen(
                                                        foodItemName = foodItem.name,
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }

                            androidx.compose.animation.AnimatedVisibility(
                                visible = showBackToTopButton,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                BackToTopButton {
                                    scope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                }
                            }

                        }
                    }
                }
            }

        }

    }
}

@Composable
fun FoodItemCard(foodItem: FoodItem, onClick: () -> Unit = {}) {
    // Customize the food item card as needed
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        VerticalDivider(
            modifier = Modifier.height(40.dp),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = foodItem.name,
            style = MaterialTheme.typography.titleMedium,
            fontFamily = RegularFont,
        )
    }
}

@Composable
fun AlphabeticalScrollComponent(
    letters: List<Char>,
    selectedLetter: Char,
    state: LazyListState = rememberLazyListState(),
    onLetterSelected: (Char) -> Unit
) {

    LazyRow(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        itemsIndexed(letters) { index, letter ->
            val isSelected = letter == selectedLetter
            val scale = if (isSelected) 1.5f else 1f // Scale the selected letter
            val selectedLetterIndex = letters.indexOf(selectedLetter)
            val distance = abs(index - selectedLetterIndex)
            val maxSize = 21
            val minSize = 18
            val letterSize = lerp(maxSize, minSize, distance.coerceAtMost(4) / 4f)

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
//                    .size(25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                        else Color.Transparent
                    )
                    .clickable { onLetterSelected(letter) }
                    .graphicsLayer(scaleX = scale, scaleY = scale),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = letter.toString(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = if (isSelected) 23.sp else letterSize.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else LocalContentColor.current
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


fun filterItemsByCategory(
    foodItems: List<FoodItem>,
    category: String,
    categoryList: List<String>
): List<FoodItem> {
    return when (category) {
        categoryList[0] -> {
            foodItems
        }

        categoryList[1] -> {
            foodItems.filter {
                it.foodType == FoodType.MainCourse
            }
        }

        categoryList[2] -> {
            foodItems.filter {
                it.foodType == FoodType.SecondaryCourse
            }
        }

        categoryList[3] -> {
            foodItems.filter {
                it.foodType == FoodType.Soup
            }
        }

        categoryList[4] -> {
            foodItems.filter {
                it.foodType == FoodType.SideDish
            }
        }

        else -> {
            foodItems
        }
    }
}
