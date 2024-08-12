package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer


data class Particle(
    var x: Float,
    var y: Float,
    val r: Float = Config.RADIUS,
) {

    fun draw(shape: ShapeRenderer, color: Color = Color.WHITE) {
        shape.color = color
        //shape.circle(x, y, r, 8)
        shape.rect(x - r, y - r, 2 * r, 2 * r)
        //shape.point(x+r, y+r, 0f)
    }
}