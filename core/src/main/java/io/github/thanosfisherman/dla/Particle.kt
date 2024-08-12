package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer


data class Particle(
    var x: Float,
    var y: Float,
    val r: Float = Config.RADIUS,
) {

    fun draw(shape: ShapeRenderer, color: Color = Color.YELLOW) {
        shape.color = color
        shape.circle(x, y, r, 8)
        //shape.rect(pos.y - r, pos.x - r, 2 * r,  r)
        //shape.point(pos.x, pos.y, 0f)
    }
}