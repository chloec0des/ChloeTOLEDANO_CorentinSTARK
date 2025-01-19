package com

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.GameScreen
import com.example.myapplication.WinScreen
import com.login.login.LoginChooseType
import com.login.login.LoginGameAccount
import com.login.login.LoginGoogle
import com.login.login.LoginPlayGames
import com.login.login.ChangePassword
import com.login.register.RegisterChooseType
import com.login.register.RegisterGameAccount
import com.login.register.RegisterMeta

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    var currentScreen by remember { mutableStateOf<LoginState>(LoginState.FirstPage) }

    fun goToFirstPage() {
        currentScreen = LoginState.FirstPage
    }

    fun goToLogin() {
        currentScreen = LoginState.LoginChooseType
    }

    fun goToRegister() {
        currentScreen = LoginState.RegisterChooseType
    }

    fun goToLoginGameAccount() {
        currentScreen = LoginState.LoginGameAccount
    }

    fun goToLoginGoogle() {
        currentScreen = LoginState.LoginGoogle
    }

    fun goToLoginMeta() {
        currentScreen = LoginState.LoginMeta
    }

    fun goToForgotPassword() {
        currentScreen = LoginState.ChangePassword
    }

    fun goToRegisterGameAccount() {
        currentScreen = LoginState.RegisterGameAccount
    }

    fun goToRegisterMeta() {
        currentScreen = LoginState.RegisterMeta
    }

    fun goBackToLoginChooseType() {
        currentScreen = LoginState.LoginChooseType
    }

    fun goBackToRegisterChooseType() {
        currentScreen = LoginState.RegisterChooseType
    }

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            when (currentScreen) {
                LoginState.FirstPage -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { goToLogin() }) { Text("Se connecter") }
                        Button(onClick = { goToRegister() }) { Text("S'inscrire") }
                    }
                }
                LoginState.LoginChooseType -> {
                    LoginChooseType(
                        onAppAccountClicked = { goToLoginGameAccount() },
                        onGoogleAccountClicked = { goToLoginGoogle() },
                        onPlayGamesAccountClicked = { goToLoginMeta() },
                        onBack = { goToFirstPage() }
                    )
                }
                LoginState.LoginGameAccount -> {
                    LoginGameAccount(
                        onLoginSuccess = {
                            navController.navigate("level") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onForgotPasswordClicked = { goToForgotPassword() },
                        onBack = { goBackToLoginChooseType() }
                    )
                }
                LoginState.LoginGoogle -> {
                    LoginGoogle(
                        onLoginSuccess = {
                            navController.navigate("level") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onBack = { goBackToLoginChooseType() }
                    )
                }
                LoginState.LoginMeta -> {
                    LoginPlayGames(
                        onLoginSuccess = {
                            navController.navigate("level") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onBack = { goBackToLoginChooseType() }
                    )
                }
                LoginState.ChangePassword -> {
                    ChangePassword(
                        onValidate = { goBackToLoginChooseType() },
                        onBack = { goBackToLoginChooseType() }
                    )
                }
                LoginState.RegisterChooseType -> {
                    RegisterChooseType(
                        onAppRegisterClicked = { goToRegisterGameAccount() },
                        onMetaRegisterClicked = { goToRegisterMeta() },
                        onBack = { goToFirstPage() }
                    )
                }
                LoginState.RegisterGameAccount -> {
                    RegisterGameAccount(
                        onRegisterSuccess = {
                            navController.navigate("level") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onBack = { goBackToRegisterChooseType() }
                    )
                }
                LoginState.RegisterMeta -> {
                    RegisterMeta(
                        onRegisterSuccess = {
                            navController.navigate("level") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onBack = { goBackToRegisterChooseType() }
                    )
                }
                else -> {}
            }
        }
        composable("level") { LevelScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen() }
        composable("game/{level}") { backStackEntry ->
            val levelIndex = backStackEntry.arguments?.getString("level")?.toIntOrNull() ?: 0
            GameScreen(
                navController = navController,
                levelIndex = levelIndex
            )
        }
        composable("win") { WinScreen(navController) }
    }
}

sealed class LoginState {
    object FirstPage : LoginState()
    object LoginChooseType : LoginState()
    object LoginGameAccount : LoginState()
    object LoginGoogle : LoginState()
    object LoginMeta : LoginState()
    object ChangePassword : LoginState()
    object RegisterChooseType : LoginState()
    object RegisterGameAccount : LoginState()
    object RegisterMeta : LoginState()
}
