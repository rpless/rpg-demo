package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2

object commands {
  sealed trait GameCommand
  case object Tick extends GameCommand
  case class ChangeDirection(vector2: Vector2) extends GameCommand
}
