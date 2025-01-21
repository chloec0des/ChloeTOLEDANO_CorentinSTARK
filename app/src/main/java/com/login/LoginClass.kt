package com.login

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.login.login.LoginChooseType
import com.login.login.LoginGameAccount
import com.login.login.LoginGoogle
import com.login.login.LoginPlayGames
import com.login.login.ResetPassword
import com.login.register.RegisterChooseType
import com.login.register.RegisterGameAccount
import com.login.register.RegisterMeta

class LoginClass {
    var currentScreen by mutableStateOf<LoginScreen>(LoginScreen.FirstPage)

    fun goToFirstPage() {
        currentScreen = LoginScreen.FirstPage
    }

    fun goToLogin() {
        currentScreen = LoginScreen.LoginChooseType
    }

    fun goToRegister() {
        currentScreen = LoginScreen.RegisterChooseType
    }

    fun goToLoginGameAccount() {
        currentScreen = LoginScreen.LoginGameAccount
    }

    fun goToLoginGoogle() {
        currentScreen = LoginScreen.LoginGoogle
    }

    fun goToLoginMeta() {
        currentScreen = LoginScreen.LoginMeta
    }

    fun goToForgotPassword() {
        currentScreen = LoginScreen.ChangePassword
    }

    fun goToRegisterGameAccount() {
        currentScreen = LoginScreen.RegisterGameAccount
    }

    fun goToRegisterMeta() {
        currentScreen = LoginScreen.RegisterMeta
    }

    fun goBackToLoginChooseType() {
        currentScreen = LoginScreen.LoginChooseType
    }

    fun goBackToRegisterChooseType() {
        currentScreen = LoginScreen.RegisterChooseType
    }

    fun goToLevelScreen() {
        currentScreen = LoginScreen.LevelScreen
    }

    @Composable
    fun RenderUI(onLevelScreen: NavHostController) {
        when (currentScreen) {
            LoginScreen.FirstPage -> {
                LoginOrRegister(
                    onRegisterClicked = { goToRegister() },
                    onLoginClicked = { goToLogin() }
                )
            }
            LoginScreen.LoginChooseType -> {
                LoginChooseType(
                    onAppAccountClicked = { goToLoginGameAccount() },
                    onGoogleAccountClicked = { goToLoginGoogle() },
                    onPlayGamesAccountClicked = { goToLoginMeta() },
                    onBack = { goToFirstPage() }
                )
            }
            LoginScreen.LoginGameAccount -> {
                LoginGameAccount(
                    onLoginSuccess = { goToLevelScreen() },
                    onForgotPasswordClicked = { goToForgotPassword() },
                    onBack = { goBackToLoginChooseType() }
                )
            }
            LoginScreen.LoginGoogle -> {
                LoginGoogle(
                    onLoginSuccess = { goToLevelScreen() },
                    onBack = { goBackToLoginChooseType() }
                )
            }
            LoginScreen.LoginMeta -> {
                LoginPlayGames(
                    onLoginSuccess = { goToLevelScreen() },
                    onBack = { goBackToLoginChooseType() }
                )
            }
            LoginScreen.ChangePassword -> {
                ResetPassword (
                    onSuccess = { goBackToLoginChooseType() },
                    onBack = { goBackToLoginChooseType() }
                )
            }
            LoginScreen.RegisterChooseType -> {
                RegisterChooseType(
                    onAppRegisterClicked = { goToRegisterGameAccount() },
                    onMetaRegisterClicked = { goToRegisterMeta() },
                    onBack = { goToFirstPage() }
                )
            }
            LoginScreen.RegisterGameAccount -> {
                RegisterGameAccount(
                    onRegisterSuccess = { goToLevelScreen() },
                    onBack = { goBackToRegisterChooseType() }
                )
            }
            LoginScreen.RegisterMeta -> {
                RegisterMeta(
                    onRegisterSuccess = { goToLevelScreen() },
                    onBack = { goBackToRegisterChooseType() }
                )
            }
            LoginScreen.LevelScreen -> {
                onLevelScreen
            }
        }
    }
}

sealed class LoginScreen {
    object FirstPage : LoginScreen()
    object LoginChooseType : LoginScreen()
    object LoginGameAccount : LoginScreen()
    object LoginGoogle : LoginScreen()
    object LoginMeta : LoginScreen()
    object ChangePassword : LoginScreen()
    object RegisterChooseType : LoginScreen()
    object RegisterGameAccount : LoginScreen()
    object RegisterMeta : LoginScreen()
    object LevelScreen : LoginScreen()
}
