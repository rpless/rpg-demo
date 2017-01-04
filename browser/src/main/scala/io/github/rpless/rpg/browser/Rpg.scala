package io.github.rpless.rpg.browser

import io.github.rpless.rpg.browser.render.CanvasRenderer
import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.Game
import io.github.rpless.rpg.singleplayer.domain.{Player, World}

import scala.concurrent.duration._
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object BrowserGame extends Game

@JSExport
object Rpg extends JSApp {

  @JSExport
  override def main(): Unit = {
    CanvasRenderer.init()
    val gameLoop = new GameLoop(0.25.seconds)
    val startingWorld = World(player = Player(Vector2(100, 100), Vector2.zero))
    gameLoop.run(
      initialWorld = startingWorld,
      game = BrowserGame,
      renderer = CanvasRenderer.render
    )
  }
}