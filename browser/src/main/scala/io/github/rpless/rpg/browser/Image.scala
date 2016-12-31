package io.github.rpless.rpg.browser

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.Render
import io.github.rpless.rpg.singleplayer.domain.World

sealed trait Image
case object BaseImage extends Image
case class Rectangle(width: Double, height: Double) extends Image
case class PlaceImage(image: Image, position: Vector2, onto: Image) extends Image

object Image {
  val PlayerSize: Double = 50
  val renderImage: Render[Image] = (world: World) => {
    val player = world.player
    val playerRect = Rectangle(PlayerSize, PlayerSize)
    PlaceImage(playerRect, player.position, BaseImage)
  }
}
