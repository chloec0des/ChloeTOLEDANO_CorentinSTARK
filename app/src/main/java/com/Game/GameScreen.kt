package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.Game.animateMovement
import com.Game.detectHorizontalAndVerticalSwipes
import kotlinx.coroutines.launch
import com.Game.findWall
import com.Game.loadLevelFromImage
import com.Game.moveOneStep

@Composable
fun GameScreen(navController: NavHostController, levelIndex: Int) {
    val context = LocalContext.current
    val labyrinth = remember { mutableStateOf<Array<Array<Int>>?>(null) }
    val topStartPosition = remember { mutableStateOf<Pair<Int, Int>?>(null) }
    val bottomStartPosition = remember { mutableStateOf<Pair<Int, Int>?>(null) }

    LaunchedEffect(levelIndex) {
        val result = loadLevelFromImage(context, levelIndex)
        if (result != null) {
            labyrinth.value = result.first
            topStartPosition.value = result.second
            bottomStartPosition.value = result.third
        }
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

@Composable
fun GameContent(
    navController: NavHostController,
    labyrinth: Array<Array<Int>>,
    topStartPosition: Pair<Int, Int>,
    bottomStartPosition: Pair<Int, Int>
) {
    var topBallPosition by remember { mutableStateOf(topStartPosition) }
    var bottomBallPosition by remember { mutableStateOf(bottomStartPosition) }
    var targetTopPosition by remember { mutableStateOf(topStartPosition) }
    var targetBottomPosition by remember { mutableStateOf(bottomStartPosition) }

    var topOffsetX by remember { mutableStateOf(0f) }
    var topOffsetY by remember { mutableStateOf(0f) }
    var bottomOffsetX by remember { mutableStateOf(0f) }
    var bottomOffsetY by remember { mutableStateOf(0f) }

    val topMoving = remember { mutableStateOf(false) }
    val bottomMoving = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(targetTopPosition) {
        if (!topMoving.value) {
            topMoving.value = true
            coroutineScope.launch {
                while (topBallPosition != targetTopPosition) {
                    val nextStep = moveOneStep(topBallPosition, targetTopPosition)
                    animateMovement(
                        topBallPosition,
                        nextStep,
                        onPositionUpdate = { x, y ->
                            topOffsetX = x
                            topOffsetY = y
                        }
                    )
                    topBallPosition = nextStep
                }
                topMoving.value = false
            }
        }
    }

    LaunchedEffect(targetBottomPosition) {
        if (!bottomMoving.value) {
            bottomMoving.value = true
            coroutineScope.launch {
                while (bottomBallPosition != targetBottomPosition) {
                    val nextStep = moveOneStep(bottomBallPosition, targetBottomPosition)
                    animateMovement(
                        bottomBallPosition,
                        nextStep,
                        onPositionUpdate = { x, y ->
                            bottomOffsetX = x
                            bottomOffsetY = y
                        }
                    )
                    bottomBallPosition = nextStep
                }
                bottomMoving.value = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalAndVerticalSwipes(
                    onSwipeUp = {
                        if (!bottomMoving.value) {
                            targetBottomPosition = findWall("up", labyrinth, bottomBallPosition)
                        }
                        if (!topMoving.value) {
                            targetTopPosition = findWall("down", labyrinth, topBallPosition)
                        }
                    },
                    onSwipeDown = {
                        if (!bottomMoving.value) {
                            targetBottomPosition = findWall("down", labyrinth, bottomBallPosition)
                        }
                        if (!topMoving.value) {
                            targetTopPosition = findWall("up", labyrinth, topBallPosition)
                        }
                    },
                    onSwipeLeft = {
                        if (!bottomMoving.value) {
                            targetBottomPosition = findWall("left", labyrinth, bottomBallPosition)
                        }
                        if (!topMoving.value) {
                            targetTopPosition = findWall("left", labyrinth, topBallPosition)
                        }
                    },
                    onSwipeRight = {
                        if (!bottomMoving.value) {
                            targetBottomPosition = findWall("right", labyrinth, bottomBallPosition)
                        }
                        if (!topMoving.value) {
                            targetTopPosition = findWall("right", labyrinth, topBallPosition)
                        }
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val rows = labyrinth.size
            val cols = labyrinth[0].size
            val cellWidth = size.width / cols
            val cellHeight = size.height / rows

            for (y in labyrinth.indices) {
                for (x in labyrinth[y].indices) {
                    val color = when (labyrinth[y][x]) {
                        0 -> Color(0xFFDBB2FF)
                        1 -> Color.Black
                        else -> Color.Gray
                    }
                    drawRect(
                        color = color,
                        topLeft = androidx.compose.ui.geometry.Offset(
                            x * cellWidth,
                            y * cellHeight
                        ),
                        size = androidx.compose.ui.geometry.Size(cellWidth, cellHeight)
                    )
                }
            }

            drawCircle(
                color = Color.Cyan,
                center = androidx.compose.ui.geometry.Offset(
                    (topBallPosition.second + 0.5f + topOffsetX) * cellWidth,
                    (topBallPosition.first + 0.5f + topOffsetY) * cellHeight
                ),
                radius = cellWidth / 2
            )

            drawCircle(
                color = Color.Yellow,
                center = androidx.compose.ui.geometry.Offset(
                    (bottomBallPosition.second + 0.5f + bottomOffsetX) * cellWidth,
                    (bottomBallPosition.first + 0.5f + bottomOffsetY) * cellHeight
                ),
                radius = cellWidth / 2
            )
        }
    }
}
