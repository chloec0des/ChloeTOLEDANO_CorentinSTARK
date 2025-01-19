package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun PointerInputScope.detectHorizontalAndVerticalSwipes(
    onSwipeUp: () -> Unit,
    onSwipeDown: () -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    val swipeThreshold = 20f
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            val changes = event.changes.firstOrNull()
            val dragAmount = changes?.positionChange()

            if (dragAmount != null) {
                when {
                    dragAmount.y < -swipeThreshold -> {
                        onSwipeUp()
                        changes.consume()
                    }
                    dragAmount.y > swipeThreshold -> {
                        onSwipeDown()
                        changes.consume()
                    }
                    dragAmount.x < -swipeThreshold -> {
                        onSwipeLeft()
                        changes.consume()
                    }
                    dragAmount.x > swipeThreshold -> {
                        onSwipeRight()
                        changes.consume()
                    }
                }
            }
        }
    }
}

@Composable
fun GameScreen(navController: NavHostController, levelIndex: Int) {
    val labyrinth = arrayOf(
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1),
        arrayOf(1, 0, 1, 0, 0, 0, 1, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 1),
        arrayOf(1, 1, 1, 0, 1, 0, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1)
    )

    val topStartPosition = Pair(1, 1)
    val bottomStartPosition = Pair(7, 7)

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

suspend fun animateMovement(
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    onPositionUpdate: (Float, Float) -> Unit
) {
    val duration = 50
    val steps = 20
    val stepDuration = duration / steps

    val dx = (end.second - start.second).toFloat() / steps
    val dy = (end.first - start.first).toFloat() / steps

    repeat(steps) {
        onPositionUpdate(it * dx, it * dy)
        delay(stepDuration.toLong())
    }
    onPositionUpdate(0f, 0f)
}

private fun findWall(direction: String, labyrinth: Array<Array<Int>>, position: Pair<Int, Int>): Pair<Int, Int> {
    var (x, y) = position
    when (direction) {
        "up" -> while (x > 0 && labyrinth[x - 1][y] == 0) x--
        "down" -> while (x < labyrinth.size - 1 && labyrinth[x + 1][y] == 0) x++
        "left" -> while (y > 0 && labyrinth[x][y - 1] == 0) y--
        "right" -> while (y < labyrinth[0].size - 1 && labyrinth[x][y + 1] == 0) y++
    }
    return Pair(x, y)
}

private fun moveOneStep(current: Pair<Int, Int>, target: Pair<Int, Int>): Pair<Int, Int> {
    val (currentX, currentY) = current
    val (targetX, targetY) = target
    return when {
        currentX < targetX -> Pair(currentX + 1, currentY)
        currentX > targetX -> Pair(currentX - 1, currentY)
        currentY < targetY -> Pair(currentX, currentY + 1)
        currentY > targetY -> Pair(currentX, currentY - 1)
        else -> current
    }
}
