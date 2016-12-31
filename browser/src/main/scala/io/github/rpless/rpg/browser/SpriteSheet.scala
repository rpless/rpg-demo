package io.github.rpless.rpg.browser

import io.github.rpless.rpg.math.Vector2
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLImageElement

case class SpriteSheet(size: Vector2, baseOffset: Vector2, image: HTMLImageElement)

object SpriteSheet {

  def apply(size: Vector2, baseOffset: Vector2, url: String): SpriteSheet = {
    val img = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    img.src = url
    SpriteSheet(size, baseOffset, img)
  }
}
