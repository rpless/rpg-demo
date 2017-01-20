package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.domain._

trait Game {
  import domain.Lenses._
  val PlayerSpeed: Double = 5

  def applyCommands(game: Game)(initialWorld: World, commands: Seq[GameCommand]): (World, Seq[GameEvent]) = {
    commands.foldLeft((initialWorld, Seq.empty[GameEvent]))({ case ((world, events), command) =>
      val (updatedWorld, event) = game.handleCommand(world, command)
      (updatedWorld, events :+ event)
    })
  }

  def handleCommand(world: World, command: GameCommand): (World, GameEvent) = command match {
    case Tick =>
      val updatedWorld = update(world)
      (updatedWorld, PlayerPositionChanged(playerPositionLens.get(updatedWorld)))
    case ChangeDirection(direction) =>
      val updatedWorld = changePlayerDirection(world, direction)
      (updatedWorld, PlayerDirectionUpdated(playerDirectionLens.get(updatedWorld)))
  }

  def update(world: World): World =
    playerDirectionLens.set(Vector2.zero)(movePlayer(world))

  private[singleplayer] def movePlayer(world: World): World = {
    val direction = playerDirectionLens.get(world)
    playerPositionLens.modify({ pos => pos + (direction.unit * PlayerSpeed) })(world)
  }

  def changePlayerDirection(world: World, direction: Vector2): World =
    playerDirectionLens.modify(dir => (dir + direction).unit)(world)
}
