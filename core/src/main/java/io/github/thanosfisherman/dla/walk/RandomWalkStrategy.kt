package io.github.thanosfisherman.dla.walk

import com.badlogic.gdx.math.MathUtils
import io.github.thanosfisherman.dla.Config.RADIUS
import io.github.thanosfisherman.dla.Config.SPEED
import io.github.thanosfisherman.dla.Particle
import io.github.thanosfisherman.dla.constrainX
import io.github.thanosfisherman.dla.constrainY

class RandomWalkStrategy(private val width: Float, private val height: Float) : WalkStrategy {
    override fun walk(particle: Particle) {
        val x = MathUtils.random(-SPEED, SPEED)
        val y = MathUtils.random(-SPEED, SPEED)
        particle.x += x
        particle.y += y
        particle.constrainX(0f + RADIUS, width - RADIUS)
        particle.constrainY(0f + RADIUS, height - RADIUS)
    }
}
