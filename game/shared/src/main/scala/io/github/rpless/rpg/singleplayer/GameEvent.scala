package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.domain.World

sealed trait GameEvent
case class WorldUpdated(world: World) extends GameEvent
case class PlayerDirectionUpdated(from: Vector2, to: Vector2) extends GameEvent
