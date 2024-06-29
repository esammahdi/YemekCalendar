package com.example.yemekcalendar.core.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "food_items")
data class FoodItemEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID") @SerializedName("id") val id: Int = 0,
    @ColumnInfo(name = "Name") @SerializedName("name") val name: String = "Place Holder",
    @ColumnInfo(name = "Calories") @SerializedName("calories") val calories: Int = 0,
    @ColumnInfo(name = "Food Type") @SerializedName("foodType") val foodType: String = "MainCourse",
    @ColumnInfo(name = "Image URL") @SerializedName("imageUrl") val imageUrl: String? = null
)


val DefaultFoodItemEntity = FoodItemEntity(
    id = 0,
    name = "Deleted Item",
    calories = 0,
    foodType = "MainCourse",
    imageUrl = null
)



