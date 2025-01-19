package com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.login.LoginClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentUser = FirebaseAuth.getInstance().currentUser
            val startDestination = if (currentUser != null) "levelScreen" else "loginScreen"

            NavHost(navController = navController, startDestination = startDestination) {
                composable("loginScreen") {
                    val loginFlow = remember { LoginClass() }
                    loginFlow.RenderUI(navController)
                }
                composable("levelScreen") {
                    LevelScreen(navController)
                }
            }
        }
    }
}
