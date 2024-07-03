package com.esammahdi.yemekcalendar.foodItemList.presentation.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esammahdi.yemekcalendar.calendar.data.interfaces.CalendarRepository
import com.esammahdi.yemekcalendar.calendar.presentation.Months
import com.esammahdi.yemekcalendar.core.data.models.CalendarDay
import com.esammahdi.yemekcalendar.core.data.models.DayType
import com.esammahdi.yemekcalendar.core.data.models.FoodItem
import com.esammahdi.yemekcalendar.core.data.models.FoodType
import com.esammahdi.yemekcalendar.core.data.room.TypeConverter
import com.esammahdi.yemekcalendar.core.other.utils.Resource
import com.esammahdi.yemekcalendar.foodItemList.data.interfaces.FoodItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val foodItemsRepository: FoodItemsRepository,
    private val calendarRepository: CalendarRepository
) : ViewModel() {
    val _foodItemDetailsState = MutableStateFlow(FoodItemDetailsState())
    val foodItemDetailsState = _foodItemDetailsState.asStateFlow()

    private var initializeCalled = false

    // This function is idempotent provided it is only called from the UI thread.
    @MainThread
    fun initialize(foodItemName: String) {
        if (initializeCalled) return
        initializeCalled = true
        fetchFoodItemByName(foodItemName)
    }

    private fun fetchFoodItemByName(name: String) {
        viewModelScope.launch {
            foodItemsRepository.getFoodItemByName(name).collectLatest { result ->
                handleFoodItemResult(result)
            }
        }
    }

    private fun selectFirstInstitution() {
        _foodItemDetailsState.value.institutionList.firstOrNull()?.let { firstInstitution ->
            _foodItemDetailsState.value =
                _foodItemDetailsState.value.copy(selectedInstitution = firstInstitution)
        }
    }

    private suspend fun fetchInstitutions() {
        calendarRepository.getInstitutionList().collectLatest { result ->
            handleInstitutionListResult(result)
        }
    }

    private suspend fun fetchCalendarDays() {
        calendarRepository.getAllCalendarDaysByInstitution(_foodItemDetailsState.value.selectedInstitution)
            .collectLatest { result ->
                handleCalendarDaysResult(result)
            }
    }

    private suspend fun handleFoodItemResult(result: Resource<FoodItem>) {
        when (result) {
            is Resource.Error -> _foodItemDetailsState.value = _foodItemDetailsState.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = result.message ?: "Default Error Message"
            )

            is Resource.Loading -> _foodItemDetailsState.value =
                _foodItemDetailsState.value.copy(isLoading = true)

            is Resource.Success -> {
                _foodItemDetailsState.value = _foodItemDetailsState.value.copy(
                    foodItem = result.data!!,
                )

                fetchInstitutions()
            }
        }
    }

    private suspend fun handleInstitutionListResult(result: Resource<List<String>>) {
        when (result) {
            is Resource.Error -> _foodItemDetailsState.value = _foodItemDetailsState.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = result.message ?: "Default Error Message"
            )

            is Resource.Loading -> _foodItemDetailsState.value =
                _foodItemDetailsState.value.copy(isLoading = true)

            is Resource.Success -> {
                _foodItemDetailsState.value = _foodItemDetailsState.value.copy(
                    institutionList = result.data ?: emptyList(), isLoading = false
                )

                selectFirstInstitution()
                fetchCalendarDays()
            }
        }
    }

    private fun handleCalendarDaysResult(result: Resource<List<CalendarDay>>) {
        when (result) {
            is Resource.Error -> _foodItemDetailsState.value = _foodItemDetailsState.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = result.message ?: "Default Error Message"
            )

            is Resource.Loading -> _foodItemDetailsState.value =
                _foodItemDetailsState.value.copy(isLoading = true)

            is Resource.Success -> {
                val map = TypeConverter().calendarDaysToMonthsMap(result.data ?: emptyList())

                //Convert the map above to a map that only have the days that the food item is served
                val daysServed = map.mapValues { (_, calendarDays) ->
                    calendarDays.filter { calendarDay ->
                        calendarDay.type == DayType.Normal &&
                                calendarDay.menu.map { it.name }
                                    .contains(_foodItemDetailsState.value.foodItem.name)
                    }
                }

                _foodItemDetailsState.value =
                    _foodItemDetailsState.value.copy(daysServed = daysServed, isLoading = false)
            }
        }
    }

    fun onInstitutionSelected(institution: String) {
        if (institution == _foodItemDetailsState.value.selectedInstitution) return

        viewModelScope.launch {
            _foodItemDetailsState.value = _foodItemDetailsState.value.copy(
                selectedInstitution = institution,
                isLoading = true
            )
            fetchCalendarDays()
        }
    }

}

data class FoodItemDetailsState(
    val foodItem: FoodItem = FoodItem(
        id = 0,
        name = "",
        calories = 0,
        foodType = FoodType.MainCourse,
        imageUrl = null
    ),
    val institutionList: List<String> = listOf(""),
    val selectedInstitution: String = "",
    val daysServed: Map<Months, List<CalendarDay>> = emptyMap(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccessful: Boolean = false,
    val errorMessage: String = ""
)