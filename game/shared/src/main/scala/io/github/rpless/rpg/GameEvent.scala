package io.github.rpless.rpg

sealed trait GameEvent
case object Tick extends GameEvent
