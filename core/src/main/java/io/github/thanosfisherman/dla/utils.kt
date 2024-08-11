package io.github.thanosfisherman.dla

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.math.Vector2
import io.github.thanosfisherman.dla.Config.RADIUS

inline fun <reified T> Pair<Int, Int>.createArray(initialValue: T?) =
    Array(this.first) { Array(this.second) { initialValue } }

fun random2D(): Vector2 {
    val randomAngleRad = randomFloatRange(0f, 1f) * MathUtils.PI2
    return Vector2(MathUtils.cos(randomAngleRad), MathUtils.sin(randomAngleRad))
}

fun Particle.constrain(maxWidth: Float, maxHeight: Float) {
    if (this.x <= 0) this.x = (0 + RADIUS)
    if (this.x >= maxWidth) this.x = (maxWidth - RADIUS)
    if (this.y <= 0) this.y = (0 + RADIUS)
    if (this.y >= maxHeight) this.y = (maxHeight - RADIUS)
}


fun randomFloatRange(min: Float, max: Float): Float {
    require(min < max) { "max must be greater than min" }
    val ran = RandomXS128()
    val result = min + ran.nextFloat() * (max - min)
    return result
}

/**
 * all inclusive
 */
fun randomIntRange(min: Int, max: Int): Int {
    require(min < max) { "max must be greater than min" }
    val ran = RandomXS128()
    val result = min + ran.nextInt((max - min) + 1)
    return result
}