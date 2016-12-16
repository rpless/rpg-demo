package io.github.rpless.rpg

object Vector2 {
  val zero: Vector2 = Vector2(0, 0)
}

case class Vector2(x: Double, y: Double) {
  def +(v: Vector2): Vector2 = this.copy(x = x + v.x, y = y + v.y)
  def -(v: Vector2): Vector2 = this.copy(x = x - v.x, y = y - v.y)
  def *(scalar: Double): Vector2 = this.copy(x = x * scalar, y = y * scalar)
  def /(scalar: Double): Vector2 = this * (1 / scalar)

  def negate: Vector2 = this * -1
  def magnitude: Double = math.sqrt(math.pow(x, 2) + math.pow(y, 2))
  def unit: Vector2 = {
    val mag = magnitude
    if (mag == 0) Vector2.zero
    else this / mag
  }
}
