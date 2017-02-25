package io.github.rpless.rpg.browser

import io.github.rpless.rpg.browser.render.CanvasRenderer
import io.github.rpless.rpg.browser.render.domain.{PlayerView, WorldView, SpriteSheet}
import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.Game
import io.github.rpless.rpg.singleplayer.domain.{Player, World}
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object BrowserGame extends Game

@JSExport
object SinglePlayerRpg extends JSApp {

  @JSExport
  override def main(): Unit = {
    val renderer = CanvasRenderer.init()
    val gameLoop = new GameLoop(BrowserGame, renderer)
    val playerStartingPosition = Vector2(100, 100)
    val startingWorld = World(player = Player(playerStartingPosition, Vector2.zero))
    val worldView = startingWorldView(playerStartingPosition)
    dom.window.requestAnimationFrame(gameLoop.loop(startingWorld, worldView))
  }

  private def startingWorldView(playerPosition: Vector2): WorldView = {
    val spriteSheet = SpriteSheet(Vector2(64, 64), "knight.png")
    WorldView(PlayerView(playerPosition, spriteSheet, 0, 0))
  }
}