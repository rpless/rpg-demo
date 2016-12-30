package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.common.Vector2

object domain {
  case class World(player: Player)
  case class Player(position: Vector2, direction: Vector2)
}
