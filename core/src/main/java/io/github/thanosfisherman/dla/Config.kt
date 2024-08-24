package io.github.thanosfisherman.dla

object Config {
    const val RADIUS = 1f
    const val SPEED = 3f
    const val ITERATIONS = 1000
    const val MAX_WALKERS = 1000
    const val MAX_WALKER_LIFESPAN = 100
    private const val SPLITTER: Int = 4

    fun toIndex(xy: Float): Int = (xy / SPLITTER).toInt()

}