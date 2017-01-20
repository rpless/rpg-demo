package io.github.rpless.rpg.singleplayer

import io.github.rpless.rpg.math.Vector2

class SimpleGameSpec extends RpgSpec {
  import domain._

  behavior of "A SimpleGame"

  object TestGame extends Game

  it should "not allow a player to move more than 5 units" in {
    check { player: Player =>
      val world = World(player)
      val updatedWorld = TestGame.movePlayer(world)
      val updatedPlayer = updatedWorld.player
      val diff = (updatedPlayer.position - player.position).magnitude
      math.floor(math.abs(diff)) <= TestGame.PlayerSpeed
    }
  }

  it should "not allow a player with direction to move" in {
    check { player: Player =>
      val world = World(player.copy(direction = Vector2.zero))
      val updatedWorld = TestGame.movePlayer(world)
      val updatedPlayer = updatedWorld.player
      updatedPlayer.position == player.position
    }
  }
}
