package io.github.thanosfisherman.dla

import com.badlogic.gdx.math.MathUtils

object Config {
    const val RADIUS = 2f
    const val SPEED = 2f
    const val ITERATIONS = 100
    const val MAX_WALKERS = 100
    const val MAX_WALKER_LIFESPAN = 1000
    private const val SPLITTER: Int = RADIUS.toInt() * 10

    fun toIndex(xy: Float): Int = MathUtils.ceil(xy / SPLITTER)
}