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
    case Tick => world.copy(player = move(world.player))
  }

  private[rpg] def move(player: Player): Player = {
    val newPosition = player.position + (player.direction.unit * PlayerSpeed)
    player.copy(position = newPosition)
  }

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
