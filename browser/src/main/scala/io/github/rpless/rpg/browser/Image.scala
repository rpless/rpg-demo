package io.github.rpless.rpg.browser

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.Render
import io.github.rpless.rpg.singleplayer.domain.World

sealed trait Image
case object BaseImage extends Image
case class PlayerImage(spriteSheet: SpriteSheet, frame: Int) extends Image
case class PlaceImage(image: Image, position: Vector2, onto: Image) extends Image

object Image {
  val KnightSpriteSheet = SpriteSheet(Vector2(64, 64), Vector2(0, 0), "knight.png")

  val renderImage: Render[Image] = (world: World) => {
    val player = world.player
    val playerRect = PlayerImage(KnightSpriteSheet, 0)
    PlaceImage(playerRect, player.position, BaseImage)
  }
}
