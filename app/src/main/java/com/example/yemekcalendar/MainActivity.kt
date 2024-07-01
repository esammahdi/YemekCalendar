package com.example.yemekcalendar

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.yemekcalendar.core.other.navigation.NavigationGraph
import com.example.yemekcalendar.core.other.navigation.Screen
import com.example.yemekcalendar.settings.presentation.viewmodel.ThemeMode
import com.example.yemekcalendar.ui.theme.YemekCalendarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    mainViewModel.initialize()

    val mainState by mainViewModel.mainState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    val appTheme = mainState.theme
    val darkTheme = when (mainState.themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val isDynamicColor =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) mainState.isDynamicColor else false

    // Determine start destination based on sign-in state
    val startDestination = if (mainState.isUserSignedIn) Screen.HomeScreen else Screen.LoginScreen

    YemekCalendarTheme(
        appTheme = appTheme,
        darkTheme = darkTheme,
        dynamicColor = isDynamicColor,
    ) {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NavigationGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }

    }
}



