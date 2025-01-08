package com

import android.content.Context
import android.graphics.BitmapFactory

object MapProcessor {
    fun convertImageToMatrix(context: Context, imageResId: Int): Array<Array<Int>> {
        val bitmap = BitmapFactory.decodeResource(context.resources, imageResId)
        val width = bitmap.width
        val height = bitmap.height
        val matrix = Array(height) { Array(width) { 0 } }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = bitmap.getPixel(x, y)
                matrix[y][x] = when {
                    pixel == 0xFFFFFFFF.toInt() -> 0 // Pathway
                    pixel == 0xFF000000.toInt() -> 1 // Wall
                    pixel == 0xFFFF0000.toInt() -> 2 // Deadly obstacle
                    pixel and 0xFF00FF00.toInt() != 0 -> 100 + ((pixel shr 8) and 0xFF) // Portal
                    pixel == 0xFF0000FF.toInt() -> 3 // Appearing obstacle
                    else -> -1
                }
            }
        }

        return matrix
    }
}