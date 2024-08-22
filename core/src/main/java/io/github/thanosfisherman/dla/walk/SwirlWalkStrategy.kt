package io.github.thanosfisherman.dla.walk

import com.badlogic.gdx.math.MathUtils.*
import io.github.thanosfisherman.dla.Particle

private const val timescale = 0.2f

class SwirlWalkStrategy(private val width: Float, private val height: Float) : WalkStrategy {
    override fun walk(particle: Particle) {

        var deltaX = particle.x
        var deltaY = particle.y

        val angle = (sin(deltaX * .034f) + sin(deltaY * .03f)) * PI * 2f

        deltaX += cos(angle) * timescale
        deltaY += sin(angle) * timescale

        particle.x = deltaX
        particle.y = deltaY
    }
}