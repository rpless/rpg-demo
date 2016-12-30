package io.github.rpless.rpg.browser

import io.github.rpless.rpg.singleplayer.Game
import io.github.rpless.rpg.singleplayer.domain._
import io.github.rpless.rpg.singleplayer.events._
import org.scalajs.dom

import scala.concurrent.duration.FiniteDuration

class GameLoop(val time: FiniteDuration) {
  val inputManager = new Input(InputContextMap.inputToDirection)

  def run(initialWorld: World, game: Game, renderer: Image => Unit): Unit = {
    var world = initialWorld
    val loop = () => {
      renderer(Image.renderImage.render(world))
      val events: Seq[GameEvent] = inputManager.events() :+ Tick
      world = events.foldLeft(world)((newWorld, event) => game.handleEvent(newWorld, event))
    }
    dom.window.setInterval(loop, time.toMillis.toDouble)
  }
}
