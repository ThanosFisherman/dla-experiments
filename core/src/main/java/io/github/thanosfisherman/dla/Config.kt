package io.github.thanosfisherman.dla

import com.badlogic.gdx.math.MathUtils

object Config {
    const val RADIUS = 1f
    const val SPEED = 2f
    const val ITERATIONS = 600
    const val MAX_WALKERS = 1000
    const val MAX_WALKER_LIFESPAN = 100
    private const val SPLITTER: Int = RADIUS.toInt() * 10

    fun toIndex(xy: Float): Int = MathUtils.ceil(xy / SPLITTER)
}