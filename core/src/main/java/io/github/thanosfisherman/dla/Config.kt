package io.github.thanosfisherman.dla

object Config {
    const val RADIUS = 0.8f
    const val SPEED = 1f
    const val ITERATIONS = 4000
    const val MAX_WALKERS = 2000
    const val MAX_WALKER_LIFESPAN = 100
    private const val SPLITTER: Int = 4

    fun toIndex(xy: Float): Int = (xy / SPLITTER).toInt()

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