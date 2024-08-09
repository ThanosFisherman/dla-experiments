package io.github.thanosfisherman.dla.walk

import com.badlogic.gdx.math.MathUtils

enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    companion object {
        fun randomDirection(): Direction {
            val size = MathUtils.random(entries.size)
            return entries[size]
        }
    }
}
