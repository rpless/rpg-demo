package io.github.rpless.rpg.browser.render.domain

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.util.Lens

object Lenses {
  val playerLens = Lens[WorldView, PlayerView](_.playerImage, (player, world) => world.copy(playerImage = player))
  val positionLens = Lens[PlayerView, Vector2](_.position, (pos, player) => player.copy(position = pos))
  val frameLens = Lens[PlayerView, Int](_.frame, (frame, player) => player.copy(frame = frame))
  val directionLens = Lens[PlayerView, Int](_.direction, (dir, player) => player.copy(direction = dir))
  val spriteSheetLens = Lens[PlayerView, SpriteSheet](_.spriteSheet, (sheet, player) => player.copy(spriteSheet = sheet))

  val playerPositionLens = playerLens andThen positionLens
  val playerFrameLens = playerLens andThen frameLens
  val playerDirectionLens = playerLens andThen directionLens
  val playerSpriteSheetLens = playerLens andThen spriteSheetLens
}
