package com.example.yemekcalendar.core.other.navigation

import kotlinx.serialization.Serializable

//sealed class Screens(val route: String) {
//    object LoginScreen : Screens(route = "Login_Screen")
//    object RegisterationScreen : Screens(route = "Registration_Screen")
//    object ResetPasswordScreen : Screens(route = "ResetPassword_Screen")
//    object HomeScreen : Screens(route = "Home_Screen")
//    object CalendarScreen : Screens(route = "Calendar_Screen")
//    object SettingsScreen : Screens(route = "Settings_Screen")
//    object FoodItemScreen : Screens(route = "FoodItem_Screen")
//    object FoodItemsScreen : Screens(route = "FoodItems_Screen/{foodItemId}")
//    object ProfileScreen : Screens(route = "Profile_Screen")
//    object AboutScreen : Screens(route = "About_Screen")
//}

sealed class Screen() {
    @Serializable
    object LoginScreen : Screen()

    @Serializable
    object RegisterationScreen : Screen()

    @Serializable
    object ResetPasswordScreen : Screen()

    @Serializable
    object HomeScreen : Screen()

    @Serializable
    object CalendarScreen : Screen()

    @Serializable
    object SettingsScreen : Screen()

    @Serializable
    object AppearanceScreen : Screen()

    @Serializable
    object CalendarSourceScreen : Screen()

    @Serializable
    object NotificationsScreen : Screen()

    @Serializable
    data class FoodDetailsScreen(
        val foodItemName: String
    ) : Screen()

    @Serializable
    object FoodListScreen : Screen()

    @Serializable
    object ProfileScreen : Screen()

    @Serializable
    object AboutScreen : Screen()
}