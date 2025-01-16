package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun GameScreen(navController: NavHostController, levelIndex: Int) {
    // Define a hardcoded labyrinth matrix
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

    // Initial positions for top and bottom balls
    val topStartPosition = Pair(1, 1)
    val bottomStartPosition = Pair(7, 7)

    var topBallPosition by remember { mutableStateOf(topStartPosition) }
    var bottomBallPosition by remember { mutableStateOf(bottomStartPosition) }

    // Check if balls meet
    LaunchedEffect(bottomBallPosition) {
        if (topBallPosition == bottomBallPosition) {
            navController.navigate("win")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Labyrinth canvas
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val rows = labyrinth.size
                val cols = labyrinth[0].size
                val cellWidth = size.width / cols
                val cellHeight = size.height / rows

                // Draw the labyrinth grid
                for (y in labyrinth.indices) {
                    for (x in labyrinth[y].indices) {
                        val color = when (labyrinth[y][x]) {
                            0 -> Color(0xFFDBB2FF) // Purple pathways
                            1 -> Color.Black       // Wall
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

                // Draw the top ball
                drawCircle(
                    color = Color.Cyan,
                    center = androidx.compose.ui.geometry.Offset(
                        (topBallPosition.second + 0.5f) * cellWidth,
                        (topBallPosition.first + 0.5f) * cellHeight
                    ),
                    radius = cellWidth / 2
                )

                // Draw the bottom ball
                drawCircle(
                    color = Color.Yellow,
                    center = androidx.compose.ui.geometry.Offset(
                        (bottomBallPosition.second + 0.5f) * cellWidth,
                        (bottomBallPosition.first + 0.5f) * cellHeight
                    ),
                    radius = cellWidth / 2
                )
            }
        }

        // Control buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ControlButton("⬆", onClick = { moveBall("up", labyrinth, bottomBallPosition) { bottomBallPosition = it } })
            ControlButton("⬇", onClick = { moveBall("down", labyrinth, bottomBallPosition) { bottomBallPosition = it } })
            ControlButton("⬅", onClick = { moveBall("left", labyrinth, bottomBallPosition) { bottomBallPosition = it } })
            ControlButton("➡", onClick = { moveBall("right", labyrinth, bottomBallPosition) { bottomBallPosition = it } })
        }
    }
}

@Composable
fun ControlButton(direction: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEB3B)), // Yellow button
        modifier = Modifier.size(64.dp) // Button size
    ) {
        Text(direction)
    }
}

// Function to move the ball
private fun moveBall(
    direction: String,
    labyrinth: Array<Array<Int>>,
    currentPosition: Pair<Int, Int>,
    updatePosition: (Pair<Int, Int>) -> Unit
) {
    val (x, y) = currentPosition
    val newPosition = when (direction) {
        "up" -> Pair(x - 1, y).takeIf { x > 0 && labyrinth[x - 1][y] == 0 }
        "down" -> Pair(x + 1, y).takeIf { x < labyrinth.size - 1 && labyrinth[x + 1][y] == 0 }
        "left" -> Pair(x, y - 1).takeIf { y > 0 && labyrinth[x][y - 1] == 0 }
        "right" -> Pair(x, y + 1).takeIf { y < labyrinth[0].size - 1 && labyrinth[x][y + 1] == 0 }
        else -> null
    }
    newPosition?.let { updatePosition(it) }
}
