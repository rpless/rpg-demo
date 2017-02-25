package io.github.rpless.rpg.browser

import io.circe.generic.auto._
import io.circe.parser._
import io.github.rpless.rpg.browser.inputs.Input
import io.github.rpless.rpg.browser.render.domain.{Lenses, WorldView}
import io.github.rpless.rpg.browser.websockets.Websocket
import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer._
import org.scalajs.dom
import org.scalajs.dom.raw.MessageEvent

class NetworkedGameLoop(renderer: WorldView => Unit) {
  val inputManager = new Input(ContextMaps.inputToGame)
  val websocket = new Websocket

  def loop(worldView: WorldView)(junk: Double): Unit = {
    renderer(worldView)
    inputManager.commands().distinct.foreach(websocket.sendCommand)
    val view = websocket.drainEvents()
      .map(contextMapIn)
      .collect({ case Right(event) => event })
      .foldLeft(worldView)(applyEvent)
    dom.window.requestAnimationFrame(loop(view))
  }

  def applyEvent(world: WorldView, event: GameEvent): WorldView = event match {
    case PlayerPositionChanged(pos) if pos == world.playerImage.position =>
      Lenses.playerFrameLens.set(0)(world)
    case PlayerPositionChanged(pos) =>
      Lenses.playerFrameLens.modify(frame => (frame + 1) % 9)(Lenses.playerPositionLens.set(pos)(world))
    case PlayerDirectionUpdated(dir) =>
      Lenses.playerDirectionLens.modify(knightDirection(dir))(world)
  }

  def contextMapIn(messageEvent: MessageEvent): Either[Throwable, GameEvent] =
    decode[GameEvent](messageEvent.data.toString)


  private def knightDirection(v: Vector2)(currentDirection: Int): Int = {
    if (v.y < 0) 0
    else if (v.y > 0) 2
    else if (v.x < 0) 1
    else if (v.x > 0) 3
    else currentDirection
  }
}
