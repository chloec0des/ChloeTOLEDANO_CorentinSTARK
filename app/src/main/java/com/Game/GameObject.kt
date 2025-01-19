package com.Game

sealed class GameObject

data class Player(val mirror: Boolean) : GameObject()

object Wall : GameObject()

object VoidArea : GameObject()

object Mirror : GameObject()