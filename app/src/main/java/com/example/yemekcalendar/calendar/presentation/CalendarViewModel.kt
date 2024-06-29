package com.example.yemekcalendar.calendar.presentation

import androidx.annotation.MainThread
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcalendar.calendar.data.interfaces.CalendarRepository
import com.example.yemekcalendar.core.data.interfaces.OnlineDatabaseRepository
import com.example.yemekcalendar.core.data.models.CalendarDay
import com.example.yemekcalendar.core.data.room.TypeConverter
import com.example.yemekcalendar.core.other.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val onlineDatabase: OnlineDatabaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarState())
    val uiState: StateFlow<CalendarState> = _uiState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true

        viewModelScope.launch {
            fetchInstitutions()
        }
    }

    private suspend fun refreshOnlineDB() {
        viewModelScope.launch {
            onlineDatabase.refreshFoodItems().collectLatest {}
            onlineDatabase.refreshInstitutions().collectLatest {}
            onlineDatabase.refreshCalendarDays().collectLatest {}
        }
    }

    private suspend fun fetchInstitutions() {
        calendarRepository.getInstitutionList().collectLatest { result ->
            handleInstitutionListResult(result)
        }
    }

    private fun selectFirstInstitution() {
        _uiState.value.institutionList.firstOrNull()?.let { firstInstitution ->
            _uiState.value = _uiState.value.copy(selectedInstitution = firstInstitution)
        }
    }

    private suspend fun fetchCalendarDays() {
        calendarRepository.getAllCalendarDaysByInstitution(_uiState.value.selectedInstitution)
            .collectLatest { result ->
                handleCalendarDaysResult(result)
            }
    }

    private suspend fun handleInstitutionListResult(result: Resource<List<String>>) {

        when (result) {
            is Resource.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    isError = true,
                    errorMessage = result.message ?: "Default Error Message"
                )
            }

            is Resource.Loading -> {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }

            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    institutionList = result.data ?: emptyList()
                )
                selectFirstInstitution()
                fetchCalendarDays()
            }
        }
    }

    private fun handleCalendarDaysResult(result: Resource<List<CalendarDay>>) {
        when (result) {
            is Resource.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    isError = true,
                    errorMessage = result.message ?: "Default Error Message"
                )
            }

            is Resource.Loading -> {
                _uiState.value = _uiState.value.copy(isLoading = true)
            }

            is Resource.Success -> {
                val map = TypeConverter().calendarDaysToMonthsMap(result.data ?: emptyList())
                _uiState.value = _uiState.value.copy(
                    calendarDays = map,
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }

    fun onForwardArrowClicked() {
        val nextMonth = if (_uiState.value.selectedMonth == Months.DECEMBER) {
            Months.JANUARY
        } else {
            Months.entries[_uiState.value.selectedMonth.ordinal + 1]
        }
        _uiState.value = _uiState.value.copy(selectedMonth = nextMonth)
    }

    fun onBackwardArrowClicked() {
        val prevMonth = if (_uiState.value.selectedMonth == Months.JANUARY) {
            Months.DECEMBER
        } else {
            Months.entries[_uiState.value.selectedMonth.ordinal - 1]
        }
        _uiState.value = _uiState.value.copy(selectedMonth = prevMonth)
    }

    fun onInstitutionSelected(institution: String) {
        if (institution == _uiState.value.selectedInstitution) return

        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(selectedInstitution = institution, isLoading = true)
            fetchCalendarDays()
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            refreshOnlineDB()
        }
    }

    fun onAddToCalendarSuccessDialogDismissed() {
        _uiState.value = _uiState.value.copy(isAddToCalendarSuccess = false)
    }
}


data class CalendarState(
    val institutionList: List<String> = emptyList(),
    val selectedInstitution: String = "Default",
    val selectedMonth: Months = Months.JANUARY,
    val calendarDays: Map<Months, List<CalendarDay>> = emptyMap(),
    val gridState: LazyGridState = LazyGridState(),
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isRefreshing: Boolean = false,
    val isAddToCalendarSuccess: Boolean = false,
    val errorMessage: String = ""
)

enum class Months {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
    JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
}











