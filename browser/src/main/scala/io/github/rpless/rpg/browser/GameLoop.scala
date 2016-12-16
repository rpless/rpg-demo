package io.github.rpless.rpg.browser

import io.github.rpless.rpg.image.Image
import io.github.rpless.rpg.{Game, GameEvent, Tick, World}
import org.scalajs.dom

import scala.concurrent.duration.FiniteDuration

class GameLoop(val time: FiniteDuration) {
  val inputManager = new Input(InputContextMap.inputToDirection)

  def run(initialWorld: World, game: Game, render: Image => Unit): Unit = {
    var world = initialWorld
    val loop = () => {
      render(game.worldToImage(world))
      val events: Seq[GameEvent] = inputManager.events() :+ Tick
      world = events.foldLeft(world)((newWorld, event) => game.handleEvent(newWorld, event))
//      println(world)
    }
    dom.window.setInterval(loop, time.toMillis.toDouble)
  }
}
