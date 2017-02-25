package io.github.rpless.rpg.distributed.singleplayer

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import io.github.rpless.rpg.singleplayer._
import io.github.rpless.rpg.singleplayer.domain.{Lenses, World}

object SinglePlayerGameActor {
  def props(game: Game, initialWorld: World): Props =
    Props(classOf[SinglePlayerGameActor], game, initialWorld)

  case class StartGame(ref: ActorRef)
}

class SinglePlayerGameActor(game: Game, initialWorld: World) extends Actor with ActorLogging {
  import SinglePlayerGameActor._


  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    log.warning("Starting Game actor")
    super.preStart()
  }

  override def receive: Receive = waitingToStart

  val waitingToStart: Receive = {
    case StartGame(ref) => context.become(gameLoop(ref, initialWorld, Seq.empty))
  }

  def gameLoop(playerRef: ActorRef, world: World, bufferedEvents: Seq[GameEvent]): Receive = {
    case Tick =>
      val (updatedWorld, event) = game.handleCommand(world, Tick)
      playerRef ! event
      playerRef ! PlayerDirectionUpdated(Lenses.playerDirectionLens.get(updatedWorld))
      log.info(s"Sending $event")
      context.become(gameLoop(playerRef, updatedWorld, Seq.empty))
    case command: ChangeDirection =>
      val (updatedWorld, event) = game.handleCommand(world, command)
      context.become(gameLoop(playerRef, updatedWorld, bufferedEvents :+ event))
      log.info(s"World Updated to $updatedWorld with a direction change $command")
    case other => log.warning(s"Got odd message $other")
  }
}
