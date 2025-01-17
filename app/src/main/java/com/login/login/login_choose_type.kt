package com.login.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun LoginChooseType(
    onAppAccountClicked: () -> Unit,
    onGoogleAccountClicked: () -> Unit,
    onMetaAccountClicked: () -> Unit,
    onBack: () -> Unit
) {
    val topColor = Color(0xFF7C1CDC)
    val bottomColor = Color(0xFFC8A2FF)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(topColor, bottomColor))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onAppAccountClicked,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = stringResource(id = R.string.game_account), color = Color.Black)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onGoogleAccountClicked,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = stringResource(id = R.string.google_account), color = Color.Black)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onMetaAccountClicked,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = stringResource(id = R.string.meta_account), color = Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = stringResource(id = R.string.back), color = Color.Black)
            }
        }
    }
}
