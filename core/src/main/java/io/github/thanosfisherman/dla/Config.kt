package io.github.thanosfisherman.dla

import com.badlogic.gdx.math.MathUtils

object Config {
    const val RADIUS = 1f
    const val SPEED = 2f
    const val ITERATIONS = 1000
    const val MAX_WALKERS = 800
    const val MAX_WALKER_LIFESPAN = 400
    private const val SPLITTER: Int = 4

    fun toIndex(xy: Float): Int = MathUtils.ceil((xy / SPLITTER))

}

/**
 * use these settings for the web
 *   const val RADIUS = 1f
 *   const val SPEED = 4f
 *   const val ITERATIONS = 1000
 *   const val MAX_WALKERS = 420
 *   const val MAX_WALKER_LIFESPAN = 20
 *   private const val SPLITTER: Int = 2
 */