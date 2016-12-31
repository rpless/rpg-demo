package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.domain._
import io.github.rpless.rpg.singleplayer.events._

trait Game {
  val PlayerSpeed: Double = 5

  def handleEvent(world: World, gameEvent: GameEvent): World = gameEvent match {
    case Tick => update(world)
    case ChangeDirection(direction) => changePlayerDirection(world, direction)
  }

  def update(world: World): World = {
    val worldWithMovedPlayer = movePlayer(world)
    worldWithMovedPlayer.copy(player = worldWithMovedPlayer.player.copy(direction = Vector2.zero))
  }

  private[singleplayer] def movePlayer(world: World): World = {
    val player: Player = world.player
    if (player.direction.magnitude == 0) world
    else {
      val updated = player.copy(position = player.position + (player.direction.unit * PlayerSpeed))
      world.copy(player = updated)
    }
  }

  def changePlayerDirection(world: World, direction: Vector2): World = {
    val player = world.player
    val updated = player.copy(direction = (player.direction + direction).unit)
    world.copy(player = updated)
  }
}
