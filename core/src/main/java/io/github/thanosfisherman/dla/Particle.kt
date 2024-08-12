package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.thanosfisherman.dla.Config.RADIUS


data class Particle(
    var x: Float,
    var y: Float,
    val r: Float = RADIUS,
)

internal fun Particle.draw(shape: ShapeRenderer, color: Color = Color.WHITE) {
    shape.color = color
    //shape.circle(x, y, r, 8)
    shape.rect(x - r, y - r, 2 * r, 2 * r)
    //shape.point(x+r, y+r, 0f)
}

internal fun Particle.constrain(maxWidth: Float, maxHeight: Float) {
    if (this.x <= 0) this.x = (0 + RADIUS)
    if (this.x >= maxWidth) this.x = (maxWidth - RADIUS)
    if (this.y <= 0) this.y = (0 + RADIUS)
    if (this.y >= maxHeight) this.y = (maxHeight - RADIUS)
}

internal fun Particle.dist2(other: Particle): Float {
    val dx = x - other.x
    val dy = y - other.y
    return dx * dx + dy * dy
}