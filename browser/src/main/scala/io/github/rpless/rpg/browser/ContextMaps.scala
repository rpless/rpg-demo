package io.github.rpless.rpg.browser

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.{ChangeDirection, GameCommand, GameEvent}

object ContextMaps {

  private val Up = Vector2(0, -1)
  private val Down = Up.negate
  private val Left = Vector2(-1, 0)
  private val Right = Left.negate

  def inputToGame: PartialFunction[String, GameCommand] = {
    case "w" => ChangeDirection(Up)
    case "a" => ChangeDirection(Left)
    case "s" => ChangeDirection(Down)
    case "d" => ChangeDirection(Right)
  }

  def gameToRender: PartialFunction[GameEvent, Any] = PartialFunction.empty
}
