package com.example.yemekcalendar.core.other.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.yemekcalendar.authentication.presentation.LoginScreen
import com.example.yemekcalendar.authentication.presentation.RegisterationScreen
import com.example.yemekcalendar.authentication.presentation.ResetPasswordScreen
import com.example.yemekcalendar.calendar.presentation.CalendarScreen
import com.example.yemekcalendar.core.presentation.HomeScreen
import com.example.yemekcalendar.foodItemList.presentation.FoodDetailsScreen
import com.example.yemekcalendar.foodItemList.presentation.FoodListScreen
import com.example.yemekcalendar.settings.presentation.AboutScreen
import com.example.yemekcalendar.settings.presentation.AppearanceScreen
import com.example.yemekcalendar.settings.presentation.CalendarSourceScreen
import com.example.yemekcalendar.settings.presentation.NotificationsScreen
import com.example.yemekcalendar.settings.presentation.ProfileScreen
import com.example.yemekcalendar.settings.presentation.SettingsScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = Screen.LoginScreen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
///////////////////////////////////              Authentication
        composable<Screen.LoginScreen> {
            LoginScreen(navController)
        }
        composable<Screen.RegisterationScreen> {
            RegisterationScreen(navController)
        }
        composable<Screen.ResetPasswordScreen> {
            ResetPasswordScreen(navController)
        }

        composable<Screen.HomeScreen> {
            HomeScreen(navController)
        }

///////////////////////////////////              Calendar
        composable<Screen.CalendarScreen> {
            CalendarScreen()
        }

///////////////////////////////////              FoodItems
        composable<Screen.FoodDetailsScreen> {
            val args = it.toRoute<Screen.FoodDetailsScreen>()
            val foodItemName = args.foodItemName

            FoodDetailsScreen(foodItemName = foodItemName, navController)
        }

        composable<Screen.FoodListScreen> {
            FoodListScreen(navController)
        }

///////////////////////////////////              Settings
        composable<Screen.SettingsScreen> {
            SettingsScreen(navController)
        }

        composable<Screen.AppearanceScreen> {
            AppearanceScreen(navController)
        }

        composable<Screen.CalendarSourceScreen> {
            CalendarSourceScreen(navController)
        }

        composable<Screen.NotificationsScreen> {
            NotificationsScreen(navController)
        }

///////////////////////////////////              Userinfo
        composable<Screen.ProfileScreen> {
            ProfileScreen(navController)
        }
///////////////////////////////////              About
        composable<Screen.AboutScreen> {
            AboutScreen(navController)
        }
    }

}
