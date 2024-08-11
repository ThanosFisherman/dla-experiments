package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer


data class Particle(
    var x: Float,
    var y: Float,
    val r: Float = Config.RADIUS,
) {
    var hu = 0f

    private val color = Color(0.0f, 0.0f, 0.8f, 1f)
    fun draw(shape: ShapeRenderer, color: Color = Color.YELLOW) {
        shape.circle(x, y, r,8)
        shape.color = color
        //shape.rect(pos.y - r, pos.x - r, 2 * r,  r)
        //shape.point(pos.x, pos.y, 0f)
    }
}