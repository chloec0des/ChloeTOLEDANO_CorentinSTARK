package com.Game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

fun loadLevelFromImage(
    context: Context,
    levelNumber: Int
): Triple<Array<Array<Int>>, Pair<Int, Int>, Pair<Int, Int>>? {
    try {
        val imageName = "level$levelNumber.png"
        Log.d("MapGenerator", "Attempting to open asset: $imageName")
        val assetManager = context.assets
        val inputStream = assetManager.open(imageName)
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        if (bitmap == null) {
            Log.e("MapGenerator", "Bitmap is null for image: $imageName")
            return null
        }
        Log.d("MapGenerator", "Bitmap loaded: width=${bitmap.width}, height=${bitmap.height}")
        val width = bitmap.width
        val height = bitmap.height
        if (width == 0 || height == 0) {
            Log.e("MapGenerator", "Invalid dimensions for \"$imageName\": width=$width, height=$height.")
            return null
        }
        val labyrinth = Array(height) { Array(width) { 0 } }
        var topPosition: Pair<Int, Int>? = null
        var bottomPosition: Pair<Int, Int>? = null
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = bitmap.getPixel(x, y)
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF
                when {
                    r == 0 && g == 0 && b == 0 -> labyrinth[y][x] = 1
                    r == 237 && g == 28 && b == 36 -> {
                        topPosition = Pair(y, x)
                        labyrinth[y][x] = 0
                    }
                    r == 63 && g == 72 && b == 204 -> {
                        bottomPosition = Pair(y, x)
                        labyrinth[y][x] = 0
                    }
                    else -> labyrinth[y][x] = 0
                }
            }
        }
        if (topPosition == null) {
            Log.e("MapGenerator", "Top position not found in \"$imageName\".")
            return null
        }
        if (bottomPosition == null) {
            Log.e("MapGenerator", "Bottom position not found in \"$imageName\".")
            return null
        }
        Log.d("MapGenerator", "Level loaded successfully: top=$topPosition, bottom=$bottomPosition")
        return Triple(labyrinth, topPosition, bottomPosition)
    } catch (e: Exception) {
        Log.e("MapGenerator", "Error loading level $levelNumber: ${e.message}", e)
        return null
    }
}
