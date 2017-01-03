package io.github.rpless.rpg.browser

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.{ChangeDirection, GameCommand}
import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent

object InputContextMap {
  private val Up = Vector2(0, -1)
  private val Down = Up.negate
  private val Left = Vector2(-1, 0)
  private val Right = Left.negate

  def inputToDirection: PartialFunction[String, GameCommand] = {
    case "w" => ChangeDirection(Up)
    case "a" => ChangeDirection(Left)
    case "s" => ChangeDirection(Down)
    case "d" => ChangeDirection(Right)
  }
}

class Input(contextMapping: PartialFunction[String, GameCommand]) {
  private var activeInputs: Map[String, Boolean] = Map.empty

  dom.window.addEventListener("keydown", (evt: KeyboardEvent) => {
    val key = evt.key
    if (contextMapping.isDefinedAt(key)) {
      activeInputs = activeInputs.updated(key, true)
    }
  })

  dom.window.addEventListener("keyup", (evt: KeyboardEvent) => {
    val key = evt.key
    if (contextMapping.isDefinedAt(key)) {
      activeInputs = activeInputs.updated(key, false)
    }
  })

  def events(): Seq[GameCommand] = {
    val active = activeInputs.collect({ case (key, true) => key }).toList
    val events = active.collect(contextMapping)
    println(events)
    events
  }
}
