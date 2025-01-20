package com.Game

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen(navController: NavHostController, levelIndex: Int) {
    val context = LocalContext.current
    val labyrinth = remember { mutableStateOf<Array<Array<GameObject>>?>(null) }
    val topStartPosition = remember { mutableStateOf<Pair<Int, Int>?>(null) }
    val bottomStartPosition = remember { mutableStateOf<Pair<Int, Int>?>(null) }

    LaunchedEffect(levelIndex) {
        val result = loadLevelFromImage(context, levelIndex)

        val updatedLabyrinth = result.map { it.copyOf() }.toTypedArray()
        val topPosition = findMirrorPlayer(result)
        val bottomPosition = findPlayer(result)

        topPosition?.let { (y, x) -> updatedLabyrinth[y][x] = VoidArea }
        bottomPosition?.let { (y, x) -> updatedLabyrinth[y][x] = VoidArea }

        labyrinth.value = updatedLabyrinth
        topStartPosition.value = topPosition
        bottomStartPosition.value = bottomPosition
    }

    if (labyrinth.value != null && topStartPosition.value != null && bottomStartPosition.value != null) {
        GameContent(
            navController = navController,
            labyrinth = labyrinth.value!!,
            topStartPosition = topStartPosition.value!!,
            bottomStartPosition = bottomStartPosition.value!!
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Failed to load the level.",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}




