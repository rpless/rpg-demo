package io.github.rpless.rpg.image

import io.github.rpless.rpg.Vector2

sealed trait Image
case object BaseImage extends Image
case class Rectangle(width: Double, height: Double) extends Image
case class PlaceImage(image: Image, position: Vector2, onto: Image) extends Image
