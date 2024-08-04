package io.github.thanosfisherman.dla

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.math.Vector2

fun random2D(): Vector2 {
    val randomAngleRad = randomFloatRange(0f, 1f) * MathUtils.PI2
    return Vector2(MathUtils.cos(randomAngleRad), MathUtils.sin(randomAngleRad))
}

fun Vector2.constrain(maxWidth: Float, maxHeight: Float) {
    if (this.x < 0) this.x = 0f
    if (this.x > maxWidth) this.x = maxWidth
    if (this.y < 0) this.y = 0f
    if (this.y > maxHeight) this.y = maxHeight
}

private fun randomFloatRange(min: Float, max: Float): Float {
    require(min < max) { "max must be greater than min" }
    val ran = RandomXS128()
    MathUtils.random()
    val result = min + ran.nextFloat() * (max - min)
    return result
}