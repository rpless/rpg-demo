package io.github.rpless.rpg.browser

import io.github.rpless.rpg.browser.inputs.Input
import io.github.rpless.rpg.browser.render.domain.{WorldView, Lenses}
import io.github.rpless.rpg.math.Vector2
import io.github.rpless.rpg.singleplayer.{PlayerDirectionUpdated, _}
import io.github.rpless.rpg.singleplayer.domain._
import org.scalajs.dom

class GameLoop(game: Game, renderer: WorldView => Unit) {
  val inputManager = new Input(ContextMaps.inputToGame)

  def loop(initialWorld: World, stuff: WorldView)(junk: Double): Unit = {
    renderer(stuff)
    val (updatedWorld, updatedStuff) = update(game, initialWorld, stuff)
    dom.window.requestAnimationFrame(loop(updatedWorld, updatedStuff))
  }

  private def update(game: Game, world: World, worldStuff: WorldView) = {
    val commands: Seq[GameCommand] = inputManager.commands() :+ Tick
    val (updatedWorld, events) = game.applyCommands(game)(world, commands)
    val updatedWorldStuff = applyEvents(worldStuff, events)
    (updatedWorld, updatedWorldStuff)
  }

  private def applyEvents(worldStuff: WorldView, events: Seq[GameEvent]): WorldView = {
    events.foldLeft(worldStuff)(applyEvent)
  }

  def applyEvent(world: WorldView, event: GameEvent): WorldView = event match {
    case PlayerPositionChanged(pos) if pos == world.playerImage.position =>
      Lenses.playerFrameLens.set(0)(world)
    case PlayerPositionChanged(pos) =>
      Lenses.playerFrameLens.modify(frame => (frame + 1) % 9)(Lenses.playerPositionLens.set(pos)(world))
    case PlayerDirectionUpdated(dir) =>
      Lenses.playerDirectionLens.modify(knightDirection(dir))(world)
  }

  private def knightDirection(v: Vector2)(currentDirection: Int): Int = {
    if (v.y < 0) 0
    else if (v.y > 0) 2
    else if (v.x < 0) 1
    else if (v.x > 0) 3
    else currentDirection
  }
}
