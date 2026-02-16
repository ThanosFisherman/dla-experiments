package io.github.thanosfisherman.dla.dlaalgo

import com.badlogic.gdx.math.MathUtils

object Config {
    const val RADIUS = 1.2f
    const val SPEED = 3f
    const val ITERATIONS = 1000
    const val MAX_WALKERS = 480
    const val MAX_WALKER_LIFESPAN = 100
    private const val SPLITTER: Int = 2

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