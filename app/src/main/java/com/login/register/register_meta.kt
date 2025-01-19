// com/login/register/RegisterMeta.kt
package com.login.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun RegisterMeta(
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var pseudo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.playgames_account))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pseudo,
            onValueChange = { pseudo = it },
            label = { Text(stringResource(id = R.string.pseudo)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Logique d'inscription Meta
            onRegisterSuccess()
        }) {
            Text(text = stringResource(id = R.string.create_account))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text(text = stringResource(id = R.string.back))
        }
    }
}
