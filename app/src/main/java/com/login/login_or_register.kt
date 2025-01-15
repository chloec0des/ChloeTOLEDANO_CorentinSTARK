package com.login

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.login.utils.LanguageManager
import com.example.myapplication.R

@Composable
fun FirstPage(
    onNewAccountClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = stringResource(id = R.string.app_name))

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour passer en français
        Button(onClick = {
            changeLanguage(context, "fr")
        }) {
            Text(text = stringResource(id = R.string.french))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bouton pour passer en anglais
        Button(onClick = {
            changeLanguage(context, "en")
        }) {
            Text(text = stringResource(id = R.string.english))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Bouton "Nouveau compte"
        Button(onClick = onNewAccountClicked) {
            Text(text = stringResource(id = R.string.new_account))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bouton "Connexion"
        Button(onClick = onLoginClicked) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}

private fun changeLanguage(context: Context, languageCode: String) {
    LanguageManager.setLocale(context, languageCode)
    // NB : Dans une vraie app, on relancerait l'Activity ou on forcerait un refresh
    // pour voir les textes mis à jour.
}
