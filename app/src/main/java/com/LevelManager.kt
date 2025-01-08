package com

import com.example.myapplication.R

object LevelManager {
    private val levels = listOf(
        R.drawable.level1,
        R.drawable.level2
    )

    fun getLevelResource(levelIndex: Int): Int {
        return levels.getOrElse(levelIndex) { R.drawable.level1 }
    }
}
