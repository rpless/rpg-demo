package io.github.rpless.rpg

case class Vector2(x: Double, y: Double) {
  def +(v: Vector2): Vector2 = this.copy(x = x + v.x, y = y + v.y)
  def -(v: Vector2): Vector2 = this.copy(x = x - v.x, y = y - v.y)
  def *(scalar: Double): Vector2 = this.copy(x = x * scalar, y = y * scalar)
  def /(scalar: Double): Vector2 = this * (1 / scalar)

  def magnitude: Double = math.sqrt(math.pow(x, 2) + math.pow(y, 2))
  def unit: Vector2 = this / this.magnitude
}
