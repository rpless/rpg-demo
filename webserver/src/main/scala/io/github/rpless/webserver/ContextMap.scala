package io.github.rpless.webserver

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import cats.syntax.either._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.github.rpless.rpg.singleplayer._

case object InvalidCommand

object ContextMap {

  def mapIncomingToGame(message: Message): Either[InvalidCommand.type, GameCommand] = message match {
    case TextMessage.Strict(text) => decode[GameCommand](text).leftMap(_ => InvalidCommand)
    case _ => Either.left(InvalidCommand)
  }

  def mapOutgoingMessage(world: GameEvent): Message = TextMessage.Strict(world.asJson.noSpaces)
}
