package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.thanosfisherman.dla.Config.RADIUS


data class Particle(
    var x: Float,
    var y: Float,
    val r: Float = RADIUS,
) {
    var lifeTime: Int = 0
}

internal fun Particle.draw(shape: ShapeRenderer, color: Color = Color.WHITE) {
    shape.color = color
    //shape.circle(x, y, r, 8)
    shape.rect(x - r, y - r, 2 * r, 2 * r)
    //shape.point(x+r, y+r, 0f)
}

internal fun Particle.constrainX(low: Float, high: Float) {
    if (this.x < low) this.x = low
    if (this.x > high) this.x = high
}

internal fun Particle.constrainY(low: Float, high: Float) {
    if (this.y < low) this.y = low
    if (this.y > high) this.y = high
}

internal fun Particle.dist2(other: Particle): Float {
    val dx = x - other.x
    val dy = y - other.y
    return dx * dx + dy * dy
}