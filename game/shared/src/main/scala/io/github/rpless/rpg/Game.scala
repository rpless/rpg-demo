package io.github.rpless.rpg

import io.github.rpless.rpg.image.{BaseImage, Image, PlaceImage, Rectangle}

trait Game {
  def handleEvent(world: World, event: GameEvent): World
  def worldToImage(world: World): Image
}

object SimpleGame extends Game {

  //-----------------------------------
  // Game Logic
  //-----------------------------------
  val PlayerSpeed: Double = 5

  def handleEvent(world: World, event: GameEvent): World = event match {
    case Tick => world.copy(player = tick(world.player))
    case ChangeDirection(direction) =>
      world.copy(player = changeDirection(world.player, direction))
  }

  private[rpg] def tick(player: Player): Player = {
    move(player).copy(direction = Vector2.zero)
  }

  private[rpg] def move(player: Player): Player = {
    if (player.direction.magnitude == 0) player
    else player.copy(position = player.position + (player.direction * PlayerSpeed))
  }

  private[rpg] def changeDirection(player: Player, direction: Vector2): Player =
    player.copy(direction = (player.direction + direction).unit)

  //-----------------------------------
  // Render Logic
  //-----------------------------------
  // TODO: Remove these in favor of a Bounding Box
  val PlayerWidth: Double = 50
  val PlayerHeight: Double = 50

  def worldToImage(world: World): Image = {
    PlaceImage(Rectangle(PlayerWidth, PlayerHeight), world.player.position, BaseImage)
  }
}
