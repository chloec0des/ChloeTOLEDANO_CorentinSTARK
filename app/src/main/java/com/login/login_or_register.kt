package com.login

import android.app.Activity
import android.os.Build
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.LanguageManager
import com.example.myapplication.R
import java.util.Locale

@Composable
fun LoginOrRegister(
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    val context = LocalContext.current
    val config = context.resources.configuration
    val currentLang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.locales[0].language
    } else {
        @Suppress("DEPRECATION")
        config.locale.language
    }
    val languageButtonText = when (currentLang) {
        "en" -> "English"
        "fr" -> "Français"
        else -> "Language"
    }

    val topColor = Color(0xFF7C1CDC)
    val bottomColor = Color(0xFFC8A2FF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    LanguageManager.changeLanguage(context)
                    (context as? Activity)?.recreate()
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = languageButtonText, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = onRegisterClicked,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.new_account),
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onLoginClicked,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = Color.Black
                )
            }
        }
    }
}
