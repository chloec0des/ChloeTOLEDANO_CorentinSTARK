package com.Game

import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.positionChange
import kotlinx.coroutines.delay

suspend fun PointerInputScope.detectHorizontalAndVerticalSwipes(
    onSwipeUp: () -> Unit,
    onSwipeDown: () -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    val swipeThreshold = 50f // Adjust the threshold as needed
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

fun findWall(direction: String, labyrinth: Array<Array<GameObject>>, position: Pair<Int, Int>): Pair<Int, Int> {
    var (x, y) = position
    when (direction) {
        "up" -> while (x > 0 && labyrinth[x - 1][y] == VoidArea) x--
        "down" -> while (x < labyrinth.size - 1 && labyrinth[x + 1][y] == VoidArea) x++
        "left" -> while (y > 0 && labyrinth[x][y - 1] == VoidArea) y--
        "right" -> while (y < labyrinth[0].size - 1 && labyrinth[x][y + 1] == VoidArea) y++
    }
    return Pair(x, y)
}

fun moveOneStep(current: Pair<Int, Int>, target: Pair<Int, Int>): Pair<Int, Int> {
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
