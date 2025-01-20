package com

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R

@Composable
fun WinScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.trophy),
            contentDescription = "Win Trophy",
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(androidx.compose.ui.graphics.Color.Yellow)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Congratulations!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.ui.graphics.Color.Green
        )
        Text(
            text = "You've successfully completed the level!",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("level") }
        ) {
            Text(text = "Choose Another Level")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
