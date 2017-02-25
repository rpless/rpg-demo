package io.github.rpless.rpg.browser

import io.github.rpless.rpg.browser.render.CanvasRenderer
import io.github.rpless.rpg.browser.render.domain.{PlayerView, SpriteSheet, WorldView}
import io.github.rpless.rpg.browser.websockets.Websocket
import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.domain.{Player, World}
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
class SinglePlayerSocketRpg extends JSApp {
  @JSExport
  override def main(): Unit = {
    val renderer = CanvasRenderer.init()
    val gameLoop = new NetworkedGameLoop(renderer)
    val playerStartingPosition = Vector2(0, 0)
    val worldView = startingWorldView(playerStartingPosition)
    dom.window.requestAnimationFrame(gameLoop.loop(worldView))
  }

  private def startingWorldView(playerPosition: Vector2): WorldView = {
    val spriteSheet = SpriteSheet(Vector2(64, 64), "knight.png")
    WorldView(PlayerView(playerPosition, spriteSheet, 0, 0))
  }
}
