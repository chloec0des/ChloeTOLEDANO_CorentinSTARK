// com/login/login/LoginChooseType.kt
package com.login.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun LoginChooseType(
    onAppAccountClicked: () -> Unit,
    onGoogleAccountClicked: () -> Unit,
    onMetaAccountClicked: () -> Unit,
    onNewAccountClicked: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.login))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onAppAccountClicked) {
            Text(text = stringResource(id = R.string.game_account))
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onGoogleAccountClicked) {
            Text(text = stringResource(id = R.string.google_account))
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onMetaAccountClicked) {
            Text(text = stringResource(id = R.string.meta_account))
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onNewAccountClicked) {
            Text(text = stringResource(id = R.string.new_account))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text(text = stringResource(id = R.string.back))
        }
    }
}
