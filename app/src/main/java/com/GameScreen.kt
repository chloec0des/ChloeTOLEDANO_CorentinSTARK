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
    // Load the labyrinth matrix
    val labyrinth = remember { MapProcessor.convertImageToMatrix(context, R.drawable.level1) }

    // Create the mirrored labyrinth by duplicating each row horizontally
    val mirroredMatrix = remember { labyrinth.map { row -> row + row }.toTypedArray() }

    // State for toggling obstacles
    var showObstacle by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Toggle every second
            showObstacle = !showObstacle
        }
    }

    // Draw the labyrinth with mirrored screens and delimitation line
    Canvas(modifier = Modifier.fillMaxSize()) {
        val rows = mirroredMatrix.size
        val cols = mirroredMatrix[0].size
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows

        // Draw the mirrored maze grid
        for (y in mirroredMatrix.indices) {
            for (x in mirroredMatrix[y].indices) {
                val color = when (mirroredMatrix[y][x]) {
                    0 -> Color.White    // Pathway
                    1 -> Color.Black    // Wall
                    2 -> Color.Red      // Deadly obstacle
                    3 -> if (showObstacle) Color.Blue else Color.Transparent // Appearing/disappearing obstacle
                    in 100..255 -> Color.Green // Portals
                    else -> Color.Gray
                }

                drawRect(
                    color = color,
                    topLeft = androidx.compose.ui.geometry.Offset(x * cellWidth, y * cellHeight),
                    size = androidx.compose.ui.geometry.Size(cellWidth, cellHeight),
                    style = Fill
                )
            }
        }

        // Draw the orange line (delimitation) at the center
        val delimiterX = (cols / 2) * cellWidth
        drawLine(
            color = Color(0xFFFFA500), // Orange color
            start = androidx.compose.ui.geometry.Offset(delimiterX, 0f),
            end = androidx.compose.ui.geometry.Offset(delimiterX, size.height),
            strokeWidth = 4f
        )
    }
}
