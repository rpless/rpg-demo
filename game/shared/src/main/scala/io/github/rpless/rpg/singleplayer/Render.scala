package io.github.rpless.rpg.singleplayer

trait Render[R] {
  def render(world: domain.World): R
}
