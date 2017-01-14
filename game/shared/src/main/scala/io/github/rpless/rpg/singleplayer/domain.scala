package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.util.Lens

object domain {
  case class World(player: Player)
  case class Player(position: Vector2, direction: Vector2)

  object Lenses {
    val playerLens = Lens[World, Player](_.player, (p, w) => w.copy(player = p))
    val positionLens = Lens[Player, Vector2](_.position, (v, p) => p.copy(position = v))
    val directionLens = Lens[Player, Vector2](_.direction, (v, p) => p.copy(direction = v))

    val playerPositionLens = playerLens andThen positionLens
    val playerDirectionLens = playerLens andThen directionLens
  }
}
