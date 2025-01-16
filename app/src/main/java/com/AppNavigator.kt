package com

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.GameScreen
import com.example.myapplication.WinScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        composable("start") { StartScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen() }
        composable("level") { LevelScreen(navController) }
        composable("game/{level}") { backStackEntry ->
            val levelIndex = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 0
            GameScreen(
                navController = navController,
                levelIndex = levelIndex // Pass levelIndex if necessary
            )
        }
        composable("win") { WinScreen(navController) }
    }
}
