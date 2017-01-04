package io.github.rpless.rpg.browser

import io.github.rpless.rpg.browser.inputs.Input
import io.github.rpless.rpg.browser.render.Image
import io.github.rpless.rpg.singleplayer.{Game, GameCommand, Tick}
import io.github.rpless.rpg.singleplayer.domain._
import org.scalajs.dom

import scala.concurrent.duration.FiniteDuration

class GameLoop(val time: FiniteDuration) {
  val inputManager = new Input(ContextMaps.inputToGame)

  def run(initialWorld: World, game: Game, renderer: Image => Unit): Unit = {
    var world = initialWorld
    val loop = () => {
      renderer(Image.renderImage(world))
      val events: Seq[GameCommand] = inputManager.events() :+ Tick
      world = events.foldLeft(world)((newWorld, event) => game.handleEvent(newWorld, event))
    }
    dom.window.setInterval(loop, time.toMillis.toDouble)
  }
}
