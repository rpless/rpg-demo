package io.github.rpless.rpg.browser.render

import io.github.rpless.rpg.browser.render.domain.WorldView
import org.scalajs.dom
import org.scalajs.dom.html

object CanvasRenderer {
  type Ctx2D = dom.CanvasRenderingContext2D

  val main = dom.document.getElementById("rpg")
  val canvas = dom.document.createElement("canvas").asInstanceOf[html.Canvas]
  val ctx = canvas.getContext("2d").asInstanceOf[Ctx2D]

  val Width: Double = 600
  val Height = Width

  def init(): WorldView => Unit = {
    canvas.width = Width.toInt
    canvas.height = Height.toInt
    main.appendChild(canvas)
    drawWorldStuff
  }

  private def drawWorldStuff(worldStuff: WorldView): Unit = {
    val player = worldStuff.playerImage
    val pos = player.position
    val frame = player.frame
    val spriteSheet = player.spriteSheet
    ctx.clearRect(0, 0, Width, Height)

    ctx.fillStyle = "green"
    ctx.fillRect(0, 0, Width, Height)

    ctx.save()
    ctx.translate(pos.x, pos.y)

    val size = spriteSheet.size
    val frameOffset = size.copy(x = size.x * frame, y = size.y * player.direction)
    ctx.drawImage(spriteSheet.image, frameOffset.x, frameOffset.y, size.x, size.y, 0, 0, size.x, size.y)

    ctx.restore()
  }
}
