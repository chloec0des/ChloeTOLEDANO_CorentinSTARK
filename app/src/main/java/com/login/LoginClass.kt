package com.login

import androidx.compose.runtime.*
import com.login.login.*
import com.login.register.*

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

    // Register : choix du type
    fun goToRegisterGameAccount() {
        currentScreen = LoginScreen.RegisterGameAccount
    }
    fun goToRegisterMeta() {
        currentScreen = LoginScreen.RegisterMeta
    }

    // Retour sur un écran antérieur
    fun goBackToLoginChooseType() {
        currentScreen = LoginScreen.LoginChooseType
    }

    fun goBackToRegisterChooseType() {
        currentScreen = LoginScreen.RegisterChooseType
    }

    @Composable
    fun RenderUI() {
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
                    onMetaAccountClicked = { goToLoginMeta() },
                    onBack = { goToFirstPage() }
                )
            }

            LoginScreen.LoginGameAccount -> {
                LoginGameAccount(
                    onLoginSuccess = {
                        goToFirstPage()
                    },
                    onForgotPasswordClicked = { goToForgotPassword() },
                    onBack = { goBackToLoginChooseType() }
                )
            }

            LoginScreen.LoginGoogle -> {
                LoginGoogle(
                    onLoginSuccess = { goToFirstPage() },
                    onBack = { goBackToLoginChooseType() }
                )
            }

            LoginScreen.LoginMeta -> {
                LoginMeta(
                    onLoginSuccess = { goToFirstPage() },
                    onBack = { goBackToLoginChooseType() }
                )
            }

            LoginScreen.ChangePassword -> {
                ChangePassword(
                    onValidate = {
                        goBackToLoginChooseType()
                    },
                    onBack = {
                        goBackToLoginChooseType()
                    }
                )
            }

            // 2) Ecrans de Register
            LoginScreen.RegisterChooseType -> {
                RegisterChooseType(
                    onAppRegisterClicked = { goToRegisterGameAccount() },
                    onMetaRegisterClicked = { goToRegisterMeta() },
                    onBack = { goToFirstPage() }
                )
            }

            LoginScreen.RegisterGameAccount -> {
                RegisterGameAccount(
                    onRegisterSuccess = {
                        goToFirstPage()
                    },
                    onBack = { goBackToRegisterChooseType() }
                )
            }

            LoginScreen.RegisterMeta -> {
                RegisterMeta(
                    onRegisterSuccess = { goToFirstPage() },
                    onBack = { goBackToRegisterChooseType() }
                )
            }
        }
    }
}

sealed class LoginScreen {
    object FirstPage : LoginScreen()

    // Login
    object LoginChooseType : LoginScreen()
    object LoginGameAccount : LoginScreen()
    object LoginGoogle : LoginScreen()
    object LoginMeta : LoginScreen()
    object ChangePassword : LoginScreen()

    // Register
    object RegisterChooseType : LoginScreen()
    object RegisterGameAccount : LoginScreen()
    object RegisterMeta : LoginScreen()
}
