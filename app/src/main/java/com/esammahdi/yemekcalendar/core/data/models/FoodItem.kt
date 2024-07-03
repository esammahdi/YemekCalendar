package com.esammahdi.yemekcalendar.core.data.models

import kotlinx.serialization.Serializable

@Serializable
class FoodItem(
    val id: Int,
    val name: String,
    val calories: Int,
    val foodType: FoodType,
    val imageUrl: String? = null
)

@Serializable
enum class FoodType {
    MainCourse,
    SecondaryCourse,
    Soup,
    SideDish
}