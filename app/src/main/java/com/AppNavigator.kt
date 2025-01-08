package com

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.GameScreen


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        composable("start") { StartScreen(navController) }
        composable("level") { LevelScreen(navController) }
        composable("game/{level}") { backStackEntry ->
            val levelIndex = backStackEntry.arguments?.getString("level")?.toInt() ?: 0
            GameScreen(levelIndex = levelIndex, navController = navController, context = LocalContext.current)
        }

    }
}
