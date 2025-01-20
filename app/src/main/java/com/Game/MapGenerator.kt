package com.Game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

fun loadLevelFromImage(
    context: Context,
    levelNumber: Int
): Array<Array<GameObject>> {
    try {
        val imageName = "level$levelNumber.png"
        val assetManager = context.assets
        val inputStream = assetManager.open(imageName)
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val width = bitmap.width
        val height = bitmap.height
        val labyrinth = Array(height) { Array(width) { VoidArea as GameObject } }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = bitmap.getPixel(x, y)
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF

                labyrinth[y][x] = when {
                    r == 0 && g == 0 && b == 0 -> Wall
                    r == 237 && g == 28 && b == 36 -> Player(mirror = true)
                    r == 63 && g == 72 && b == 204 -> Player(mirror = false)
                    r == 255 && g == 127 && b == 39 -> Mirror
                    else -> VoidArea
                }
            }
        }

        return labyrinth
    } catch (e: Exception) {
        Log.e("MapGenerator", "Error loading level $levelNumber: ${e.message}", e)
        throw e
    }
}

fun findPlayer(labyrinth: Array<Array<GameObject>>): Pair<Int, Int>? {
    for (y in labyrinth.indices) {
        for (x in labyrinth[y].indices) {
            if (labyrinth[y][x] is Player && !(labyrinth[y][x] as Player).mirror) {
                return Pair(y, x)
            }
        }
    }
    return null
}

fun findMirrorPlayer(labyrinth: Array<Array<GameObject>>): Pair<Int, Int>? {
    for (y in labyrinth.indices) {
        for (x in labyrinth[y].indices) {
            if (labyrinth[y][x] is Player && (labyrinth[y][x] as Player).mirror) {
                return Pair(y, x)
            }
        }
    }
    return null
}
