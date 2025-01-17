package com.login.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun RegisterChooseType(
    onAppRegisterClicked: () -> Unit,
    onMetaRegisterClicked: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.create_account))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onAppRegisterClicked) {
            Text(text = stringResource(id = R.string.game_account))
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onMetaRegisterClicked) {
            Text(text = stringResource(id = R.string.meta_account))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text(text = stringResource(id = R.string.back))
        }
    }
}
