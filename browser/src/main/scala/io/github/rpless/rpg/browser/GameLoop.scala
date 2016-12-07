package io.github.rpless.rpg.browser

import io.github.rpless.rpg.image.Image
import io.github.rpless.rpg.{Game, Tick, World}
import org.scalajs.dom

import scala.concurrent.duration.FiniteDuration

class GameLoop(val time: FiniteDuration) {

  def run(initialWorld: World, game: Game, render: Image => Unit): Unit = {
    var world = initialWorld
    val loop = () => {
      println("Hello")
      render(game.worldToImage(world))
      world = game.handleEvent(world, Tick)
    }
    dom.window.setInterval(loop, time.toMillis.toDouble)
  }
}
