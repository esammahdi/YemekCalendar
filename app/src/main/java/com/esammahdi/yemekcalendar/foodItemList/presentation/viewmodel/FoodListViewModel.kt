package com.esammahdi.yemekcalendar.foodItemList.presentation.viewmodel

import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.core.data.interfaces.OnlineDatabaseRepository
import com.esammahdi.yemekcalendar.core.data.models.FoodItem
import com.esammahdi.yemekcalendar.core.other.utils.Resource
import com.esammahdi.yemekcalendar.foodItemList.data.interfaces.FoodItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodListViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val foodItemsRepository: FoodItemsRepository,
    private val onlineDatabase: OnlineDatabaseRepository,
) : ViewModel() {
    val _foodItemsState = MutableStateFlow(FoodItemsState())
    val foodItemsState = _foodItemsState.asStateFlow()
    val resources = appContext.resources
    private var foodItems = listOf<FoodItem>()

    private var initializeCalled = false

    val categoryList = listOf(
        resources.getString(R.string.all_categories),
        resources.getString(R.string.main_course),
        resources.getString(R.string.second_course),
        resources.getString(R.string.soup),
        resources.getString(R.string.side_dish),
    )


    // This function is idempotent provided it is only called from the UI thread.
    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        viewModelScope.launch {
            fetchFoodItems()
        }
    }


    private fun refreshFoodItems() {
        viewModelScope.launch {
            onlineDatabase.refreshFoodItems().collectLatest {}
        }
    }


    private suspend fun fetchFoodItems() {
        foodItemsRepository.getAllFoodItems().collectLatest { result ->
            handleFoodItemsResult(result)
        }
    }

    private fun handleFoodItemsResult(result: Resource<List<FoodItem>>) {
        when (result) {
            is Resource.Error -> _foodItemsState.value = _foodItemsState.value.copy(
                isLoading = false,
                isRefreshing = false,
                isError = true,
                errorMessage = result.message ?: "Default Error Message"
            )

            is Resource.Loading -> _foodItemsState.value =
                _foodItemsState.value.copy(isLoading = true)

            is Resource.Success -> {
                val letters = getLetters(result.data ?: emptyList())
                foodItems = (result.data ?: emptyList()).sortedBy { it.name }

//                val groupedFoodItems = sortedFoodItems.groupBy { it.name.first().uppercaseChar() }

                _foodItemsState.value = _foodItemsState.value.copy(
                    foodItems = foodItems,
                    categoryList = categoryList,
                    selectedCategory = categoryList[0],
                    letters = letters,
                    isLoading = false,
                    isRefreshing = false,
                    isSuccessful = true
                )
            }
        }
    }

    private fun getLetters(foodItems: List<FoodItem>): List<Char> {
        val sortedFoodItems = foodItems.sortedBy { it.name }
        val groupedFoodItems = sortedFoodItems.groupBy { it.name.first().uppercaseChar() }
        val letters = groupedFoodItems.keys.toList().sorted()

        return letters
    }


    fun onSelectedCategoryChanged(category: String) {

        _foodItemsState.value = when (category) {
            categoryList[1] -> {
//                val items = foodItems.filter {
//                    it.foodType == FoodType.MainCourse
//                }
                _foodItemsState.value.copy(
//                    foodItems = items,
                    selectedCategory = category,
                )
            }

            categoryList[2] -> {
//                val items = foodItems.filter {
//                    it.foodType == FoodType.SecondaryCourse
//                }
                _foodItemsState.value.copy(
//                    foodItems = items,
                    selectedCategory = category,
                )
            }

            categoryList[3] -> {
//                val items = foodItems.filter {
//                    it.foodType == FoodType.Soup
//                }
                _foodItemsState.value.copy(
//                    foodItems = items,
                    selectedCategory = category,
                )
            }

            categoryList[4] -> {
//                val items = foodItems.filter {
//                    it.foodType == FoodType.SideDish
//                }
                _foodItemsState.value.copy(
//                    foodItems = items,
                    selectedCategory = category,
                )
            }

            else -> {
                _foodItemsState.value.copy(
//                    foodItems = foodItems,
                    selectedCategory = category,
                )
            }
        }
    }

    fun onRefresh() {
        _foodItemsState.value = _foodItemsState.value.copy(isRefreshing = true)
        refreshFoodItems()
    }
}

data class FoodItemsState(
    val foodItems: List<FoodItem> = emptyList(),
    val categoryList: List<String> = emptyList(),
    val selectedCategory: String = "",
    val letters: List<Char> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccessful: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String = ""
)