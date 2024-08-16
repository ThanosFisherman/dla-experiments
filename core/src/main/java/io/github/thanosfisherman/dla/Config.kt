package io.github.thanosfisherman.dla

object Config {
    const val RADIUS = 1f
    const val SPEED = 3f
    const val ITERATIONS = 1000
    const val MAX_WALKERS = 400
    const val MAX_WALKER_LIFESPAN = 20
    private const val SPLITTER: Int = RADIUS.toInt() * 2

    fun toIndex(xy: Float): Int = (xy / SPLITTER).toInt()

// Use these values for the web

    /*
    const val RADIUS = 1f
    const val SPEED = 3f
    const val ITERATIONS = 1000
    const val MAX_WALKERS = 400
    const val MAX_WALKER_LIFESPAN = 20
    private const val SPLITTER: Int = RADIUS.toInt() * 2

    */
}