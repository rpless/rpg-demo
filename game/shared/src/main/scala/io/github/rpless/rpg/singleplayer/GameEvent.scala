package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.domain.World

sealed trait GameEvent
case class PlayerPositionChanged(to: Vector2) extends GameEvent
case class PlayerDirectionUpdated(to: Vector2) extends GameEvent
