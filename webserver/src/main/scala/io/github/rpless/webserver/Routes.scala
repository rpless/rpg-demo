package io.github.rpless.webserver

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.ws.Message
import akka.http.scaladsl.server.Directives._
import akka.stream.Attributes
import akka.stream.scaladsl.Flow
import io.github.rpless.rpg.distributed.singleplayer.SinglePlayerGame
import io.github.rpless.rpg.singleplayer.Game

object SinglePlayer extends Game

object Routes {
  def apply(actorSystem: ActorSystem) =
    path("")(getFromResource("web/index.html")) ~
    path("game.js")(getFromResource("browser-fastopt.js")) ~
    path("singleplayer")(handleWebSocketMessages(singlePlayerSocket(actorSystem, SinglePlayer))) ~
    getFromResourceDirectory("web")


  private def singlePlayerSocket(actorSystem: ActorSystem, game: Game): Flow[Message, Message, _] = {
    val gameFlow = SinglePlayerGame(actorSystem, game).flow
    Flow[Message]
      .map(ContextMap.mapIncomingToGame)
      .collect({ case Right(command) => command })
      .via(gameFlow)
      .map(ContextMap.mapOutgoingMessage)
      .withAttributes(Attributes.logLevels(onElement = Logging.WarningLevel))
  }
}
