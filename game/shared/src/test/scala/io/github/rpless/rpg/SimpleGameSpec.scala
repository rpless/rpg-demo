package io.github.rpless.rpg

class SimpleGameSpec extends RpgSpec {
  import SimpleGame._

  behavior of "A SimpleGame"

  it should "not allow a player to move more than 5 units" in {
    check { player: Player =>
      val updatedPlayer = move(player)
      val dif = (updatedPlayer.position - player.position).magnitude
      math.floor(math.abs(dif)) <= SimpleGame.PlayerSpeed
    }
  }
}
