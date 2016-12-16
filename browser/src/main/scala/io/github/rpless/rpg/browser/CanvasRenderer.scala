package io.github.rpless.rpg.browser

import io.github.rpless.rpg.image.{BaseImage, Image, PlaceImage, Rectangle}
import org.scalajs.dom
import org.scalajs.dom.html

object CanvasRenderer {
  type Ctx2D = dom.CanvasRenderingContext2D

  val main = dom.document.getElementById("rpg")
  val canvas = dom.document.createElement("canvas").asInstanceOf[html.Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[Ctx2D]

  val Width: Double = 600
  val Height = Width

  def init(): Unit = {
    canvas.width = Width.toInt
    canvas.height = Height.toInt
    main.appendChild(canvas)
  }

  def render(image: Image): Unit = {
    ctx.clearRect(0, 0, Width, Height)
    renderRec(image)
  }

  private def renderRec(image: Image): Unit = {
    image match {
      case BaseImage =>
        ctx.fillStyle = "black"
        ctx.fillRect(0, 0, Width, Height)
      case Rectangle(width, height) =>
        ctx.fillStyle = "blue"
        ctx.fillRect(0, 0, width, height)
      case PlaceImage(i, pos, base) =>
        renderRec(base)
        ctx.save()
        ctx.translate(pos.x, pos.y)
        renderRec(i)
        ctx.restore()
    }
  }
}
