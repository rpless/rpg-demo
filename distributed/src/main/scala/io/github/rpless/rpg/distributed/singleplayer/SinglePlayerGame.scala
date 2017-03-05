package io.github.rpless.rpg.distributed.singleplayer

import akka.NotUsed
import akka.actor._
import akka.stream.{FlowShape, OverflowStrategy}
import akka.stream.scaladsl._
import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.domain.{Player, World}
import io.github.rpless.rpg.singleplayer.{Game, GameCommand, GameEvent, Tick}

import scala.concurrent.duration._

case class SinglePlayerGame(actorSystem: ActorSystem, game: Game) {
  import SinglePlayerGameActor._
  private val initialWorld: World = World(Player(Vector2.zero, Vector2.zero))
  private val gameActor: ActorRef = actorSystem.actorOf(SinglePlayerGameActor.props(game, initialWorld))

  val flow: Flow[GameCommand, GameEvent, NotUsed] = {
    val in = Sink.actorRef[GameCommand](gameActor, ())
    val out = Source.actorRef[GameEvent](bufferSize = Int.MaxValue, OverflowStrategy.fail)
      .mapMaterializedValue(gameActor ! StartGame(_))
    Flow.fromSinkAndSource(eventFlow.to(in), out)
  }

  private def eventFlow = {
    val timeSource = Source.tick(1.second, 0.10.seconds, Tick)
    Flow.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._
      val merge = b.add(Merge[GameCommand](2, eagerComplete = true))

      timeSource ~> merge.in(0)
      FlowShape(merge.in(1), merge.out)
    })
  }
}
