package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.navigation.NavHostController
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.MapProcessor
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun GameScreen(levelIndex: Int, navController: NavHostController, context: Context) {
    // Load the labyrinth matrix from the image resource
    val labyrinth = remember { MapProcessor.convertImageToMatrix(context, R.drawable.level1) }

    // Set the user's starting position
    var userPosition by remember { mutableStateOf(Pair(labyrinth.size - 2, labyrinth[0].size - 2)) } // Example: Bottom-right

    // State for toggling obstacles
    var showObstacle by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Toggle every second
            showObstacle = !showObstacle
        }
    }

    // Draw the labyrinth filling the entire screen
    Canvas(modifier = Modifier.fillMaxSize()) {
        val rows = labyrinth.size
        val cols = labyrinth[0].size
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows

        // Draw the labyrinth grid
        for (y in labyrinth.indices) {
            for (x in labyrinth[y].indices) {
                val color = getColorForCell(labyrinth[y][x], showObstacle)
                drawRect(
                    color = color,
                    topLeft = androidx.compose.ui.geometry.Offset(x * cellWidth, y * cellHeight),
                    size = androidx.compose.ui.geometry.Size(cellWidth, cellHeight),
                    style = Fill
                )
            }
        }

        // Draw the user's position as a yellow ball
        drawCircle(
            color = Color.Yellow,
            center = androidx.compose.ui.geometry.Offset(
                (userPosition.second + 0.5f) * cellWidth,
                (userPosition.first + 0.5f) * cellHeight
            ),
            radius = cellWidth / 2 // Ball fits into the cell
        )
    }
}

// Helper function to determine the color of each cell
private fun getColorForCell(cellValue: Int, showObstacle: Boolean): Color {
    return when (cellValue) {
        0 -> Color.White    // Pathway
        1 -> Color.Black    // Wall
        2 -> Color.Red      // Deadly obstacle
        3 -> if (showObstacle) Color.Blue else Color.Transparent // Appearing obstacle
        in 100..255 -> Color.Green // Portals
        else -> Color.Gray
    }
}
