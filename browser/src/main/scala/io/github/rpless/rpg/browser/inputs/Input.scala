package io.github.rpless.rpg.browser.inputs

import io.github.rpless.rpg.singleplayer.GameCommand
import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent

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
    active.collect(contextMapping)
  }
}
