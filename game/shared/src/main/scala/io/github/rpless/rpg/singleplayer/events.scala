package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2

object events {
  sealed trait GameEvent
  case object Tick extends GameEvent
  case class ChangeDirection(vector2: Vector2) extends GameEvent
}
