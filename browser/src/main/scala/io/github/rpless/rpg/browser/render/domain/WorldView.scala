package io.github.rpless.rpg.browser.render.domain

import io.github.rpless.rpg.math.Vector2

case class WorldView(playerImage: PlayerView)
case class PlayerView(position: Vector2, spriteSheet: SpriteSheet, direction: Int, frame: Int)
