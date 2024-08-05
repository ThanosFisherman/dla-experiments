package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.*
import com.badlogic.gdx.math.Vector2
import ktx.math.vec2


data class Particle(
    var x: Float = 0f,
    var y: Float = 0f,
    val r: Float = 0f,
) {

    constructor(r: Float, pos: Vector2) : this(pos.x, pos.y, r)

    private val pos = vec2(x, y)

    fun checkStuck(others: List<Particle>): Boolean {
        for (other in others) {
            val d = pos.dst(other.pos)
            if (d < r * 2) {
                return true
            }
        }
        return false
    }

    fun updateWalk(worldWidth: Float, worldHeight: Float) {
        pos.add(random2D())
        pos.constrain(worldWidth, worldHeight)
    }

    fun updateBias(worldWidth: Float, worldHeight: Float) {
        val angle = atan2((worldHeight / 2) - pos.y, (worldWidth / 2) - pos.x) * 2f
        pos.add(-cos(angle), -sin(angle))
    }

    var hu = 0f

    private val color = Color(0.0f, 0.0f, 0.8f, 1f)
    fun draw(shape: ShapeRenderer) {
        shape.circle(pos.y - r, pos.x - r, r )
        color.r = hu * 0.0006f
        //color.b = hu * 0.001f
        shape.color = color

        //shape.rect(pos.y - r, pos.x - r, 2 * r,  r)
        //shape.point(pos.x, pos.y, 0f)
    }

    private fun distSq(a: Vector2, b: Vector2): Float {
        val dx: Float = a.x - b.x
        val dy: Float = a.y - b.y
        return dx * dx + dy * dy
    }

    companion object {

        fun randomPoint(r: Float, worldWidth: Float, worldHeight: Float): Particle {
            val i = floor(random(4).toFloat())
            return when (i) {
                0 -> Particle(r, vec2(random(worldWidth), 0f))
                1 -> Particle(r, vec2(random(worldWidth), worldHeight))
                2 -> Particle(r, vec2(0f, random(worldHeight)))
                else -> Particle(r, vec2(worldWidth, random(worldHeight)))
            }
        }
    }
}