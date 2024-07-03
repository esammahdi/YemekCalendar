package com.esammahdi.yemekcalendar.core.other.navigation

import kotlinx.serialization.Serializable
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