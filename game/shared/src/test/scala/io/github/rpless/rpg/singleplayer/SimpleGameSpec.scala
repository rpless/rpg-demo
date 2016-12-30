package io.github.rpless.rpg.singleplayer

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
}
