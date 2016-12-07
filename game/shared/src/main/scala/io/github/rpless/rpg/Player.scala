package io.github.rpless.rpg

import java.util.UUID

sealed trait Entity {
  val id: UUID
}

case class World(player: Player)

case class Player(id: UUID, position: Vector2, direction: Vector2) extends Entity