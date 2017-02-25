package io.github.rpless.rpg.browser.websockets

import io.circe.generic.auto._
import io.circe.scalajs._
import io.github.rpless.rpg.singleplayer.GameCommand
import org.scalajs.dom
import org.scalajs.dom.raw.{MessageEvent, WebSocket => JSWebSocket}

class Websocket {
  private var incomingBuffer: Seq[MessageEvent] = Seq.empty
  private val socket = new JSWebSocket(url)
  socket.onmessage = { (event: MessageEvent) => incomingBuffer = incomingBuffer :+ event }

  def sendCommand(gameCommand: GameCommand): Unit = {
    convertJsToJson(gameCommand.asJsAny).fold(_ => (), v => socket.send(v.noSpaces))
  }

  def drainEvents(): Seq[MessageEvent] = {
    val events = incomingBuffer
    incomingBuffer = Seq.empty
    events
  }

  private def url: String = {
    val protocol = if (dom.document.location.protocol == "https:") "wss" else "ws"
    s"$protocol://${dom.document.location.host}/singleplayer"
  }
}
