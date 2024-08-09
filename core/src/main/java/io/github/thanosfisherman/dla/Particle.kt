package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

const val RADIUS: Float = 1f

data class Particle(
    var x: Float,
    var y: Float,
    val r: Float = RADIUS,
) {
    var hu = 0f

    private val color = Color(0.0f, 0.0f, 0.8f, 1f)
    fun draw(shape: ShapeRenderer) {

        shape.circle(x - r, y - r, r)
        color.r = hu * 0.0006f
        //color.b = hu * 0.001f
        shape.color = color

        //shape.rect(pos.y - r, pos.x - r, 2 * r,  r)
        //shape.point(pos.x, pos.y, 0f)
    }

    fun dist2(other: Particle): Float {
        val dx: Float = x - other.x
        val dy: Float = y - other.y
        return dx * dx + dy * dy
    }
}