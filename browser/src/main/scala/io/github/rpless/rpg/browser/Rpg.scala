package io.github.rpless.rpg.browser

import java.util.UUID

import io.github.rpless.rpg._

import scala.concurrent.duration._
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
object Rpg extends JSApp {

  @JSExport
  override def main(): Unit = {
    CanvasRenderer.init()
    val gameLoop = new GameLoop(0.25.seconds)
    val startingWorld = World(player = Player(UUID.randomUUID(), Vector2(100, 100), Vector2.zero))
    gameLoop.run(
      initialWorld = startingWorld,
      game = SimpleGame,
      render = CanvasRenderer.render
    )
  }
}