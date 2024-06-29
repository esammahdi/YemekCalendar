package com.example.yemekcalendar.core.data.room

import androidx.room.TypeConverter
import com.example.yemekcalendar.calendar.presentation.Months
import com.example.yemekcalendar.core.data.models.CalendarDay
import com.example.yemekcalendar.core.data.models.DayType
import com.example.yemekcalendar.core.data.models.FoodItem
import com.example.yemekcalendar.core.data.models.FoodType
import com.example.yemekcalendar.core.data.room.entities.CalendarDayEntity
import com.example.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.example.yemekcalendar.core.data.room.entities.InstitutionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar
import java.util.Date

class TypeConverter {
    fun foodItemEntityToFoodItem(foodItemEntity: FoodItemEntity): FoodItem {
        val foodType = when (foodItemEntity.foodType) {
            "MainCourse" -> FoodType.MainCourse
            "SecondaryCourse" -> FoodType.SecondaryCourse
            "Soup" -> FoodType.Soup
            "SideDish" -> FoodType.SideDish
            else -> FoodType.MainCourse
        }

        return FoodItem(
            id = foodItemEntity.id,
            name = foodItemEntity.name,
            calories = foodItemEntity.calories,
            foodType = foodType,
            imageUrl = foodItemEntity.imageUrl
        )
    }

    fun foodItemToFoodItemEntity(foodItem: FoodItem): FoodItemEntity {
        val foodType = when (foodItem.foodType) {
            FoodType.MainCourse -> "MainCourse"
            FoodType.SecondaryCourse -> "SecondaryCourse"
            FoodType.Soup -> "Soup"
            FoodType.SideDish -> "SideDish"
        }

        return FoodItemEntity(
            id = foodItem.id,
            name = foodItem.name,
            calories = foodItem.calories,
            foodType = foodType,
            imageUrl = foodItem.imageUrl
        )

    }

    fun calendarDayEntityToCalendarDay(calendarDayEntity: CalendarDayEntity): CalendarDay {
        val dayType = when (calendarDayEntity.type) {
            "Normal" -> DayType.Normal
            "Weekend" -> DayType.Weekend
            "Special Holiday" -> DayType.SpecialHoliday
            "Updated" -> DayType.Updated
            "Canceled" -> DayType.Canceled
            else -> DayType.Canceled
        }

        return CalendarDay(
            date = Date(calendarDayEntity.date * 1000),
            menu = calendarDayEntity.menu.map { foodItemEntityToFoodItem(it) },
            type = dayType
        )
    }

    fun calendarDayToCalendarDayEntity(
        calendarDay: CalendarDay,
        institution: String
    ): CalendarDayEntity {
        val dayType = when (calendarDay.type) {
            DayType.Normal -> "Normal"
            DayType.Weekend -> "Weekend"
            DayType.SpecialHoliday -> "Special Holiday"
            DayType.Updated -> "Updated"
            DayType.Canceled -> "Canceled"
        }
        return CalendarDayEntity(
            institution = institution,
            date = calendarDay.date.time,
            menu = calendarDay.menu.map { foodItemToFoodItemEntity(it) },
            type = dayType
        )
    }

    fun institutionToInstitutionEntity(institution: String): InstitutionEntity {
        return InstitutionEntity(name = institution)
    }

    fun institutionEntityToInstitution(institutionEntity: InstitutionEntity): String {
        return institutionEntity.name
    }

    fun calendarDaysToMonthsMap(calendarDays: List<CalendarDay>): Map<Months, List<CalendarDay>> {
        return calendarDays.groupBy {
            Calendar.getInstance().apply { time = it.date }.get(Calendar.MONTH)
        }.mapKeys { (monthIndex, _) -> Months.entries[monthIndex] }
    }

}

class FoodItemEntityTypeConverter {
    @TypeConverter
    fun foodItemEntityListToString(list: List<FoodItemEntity>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToFoodItemEntityList(value: String): List<FoodItemEntity> {
        return Gson().fromJson(value, object : TypeToken<List<FoodItemEntity>>() {}.type)
    }
}
