package io.github.rpless.rpg

sealed trait GameEvent
case object Tick extends GameEvent
case class ChangeDirection(vec: Vector2) extends GameEvent
