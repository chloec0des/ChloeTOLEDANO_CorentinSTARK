package com.Game

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

@SuppressLint("UnrememberedMutableState")
@Composable
fun GameContent(
    navController: NavHostController,
    labyrinth: Array<Array<GameObject>>,
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

    startWinChecker(
        labyrinth = labyrinth,
        topBallPosition = derivedStateOf { topBallPosition },
        bottomBallPosition = derivedStateOf { bottomBallPosition },
        navController = navController,
        topMoving = topMoving,
        bottomMoving = bottomMoving
    )

    LaunchedEffect(targetTopPosition) {
        if (!topMoving.value) {
            topMoving.value = true
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

    LaunchedEffect(targetBottomPosition) {
        if (!bottomMoving.value) {
            bottomMoving.value = true
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
                        VoidArea -> Color(0xFFC8A2FF)
                        Wall -> Color.Black
                        Mirror -> Color.White
                        else -> Color.Gray
                    }
                    drawRect(
                        color = color,
                        topLeft = Offset(
                            x * cellWidth,
                            y * cellHeight
                        ),
                        size = Size(cellWidth, cellHeight)
                    )
                }
            }

            drawCircle(
                color = Color.Cyan,
                center = Offset(
                    (topBallPosition.second + 0.5f + topOffsetX) * cellWidth,
                    (topBallPosition.first + 0.5f + topOffsetY) * cellHeight
                ),
                radius = cellWidth / 2
            )

            drawCircle(
                color = Color.Yellow,
                center = Offset(
                    (bottomBallPosition.second + 0.5f + bottomOffsetX) * cellWidth,
                    (bottomBallPosition.first + 0.5f + bottomOffsetY) * cellHeight
                ),
                radius = cellWidth / 2
            )
        }
    }
}

@Composable
fun startWinChecker(
    labyrinth: Array<Array<GameObject>>,
    topBallPosition: State<Pair<Int, Int>>,
    bottomBallPosition: State<Pair<Int, Int>>,
    navController: NavHostController,
    topMoving: State<Boolean>,
    bottomMoving: State<Boolean>
) {
    var showPositionPopup by remember { mutableStateOf(false) }
    var mirrorTestPopup by remember { mutableStateOf(false) }
    var topValues by remember { mutableStateOf(Pair(0, 0)) }
    var bottomValues by remember { mutableStateOf(Pair(0, 0)) }

    LaunchedEffect(Unit) {
        while (true) {
            topValues = topBallPosition.value
            bottomValues = bottomBallPosition.value

            delay(50)
            val (xTop, yTop) = topValues
            val (xBottom, yBottom) = bottomValues
            val middleX = (xTop + xBottom) / 2
            val middleY = yTop
            if (yTop == yBottom && kotlin.math.abs(xTop - xBottom) == 2 &&
                !bottomMoving.value && !topMoving.value && labyrinth[middleX][middleY] is Mirror
            ) {
                navController.navigate("win")
                break
            }
        }
    }
}
