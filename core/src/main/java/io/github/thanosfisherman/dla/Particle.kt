package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.floor
import com.badlogic.gdx.math.MathUtils.random
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

    fun draw(shape: ShapeRenderer) {
        //shape.circle(pos.x - r, pos.y - r, r * 2)
        shape.rect(pos.x - r, pos.y - r, 2 * r, 2 * r)
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