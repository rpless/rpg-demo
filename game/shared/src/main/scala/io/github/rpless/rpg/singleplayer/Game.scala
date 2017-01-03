package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.domain._
import io.github.rpless.rpg.singleplayer.commands._

trait Game {
  import domain.Lenses._
  val PlayerSpeed: Double = 5

  def handleEvent(world: World, command: GameCommand): World = command match {
    case Tick => update(world)
    case ChangeDirection(direction) => changePlayerDirection(world, direction)
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
