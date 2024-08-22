package io.github.thanosfisherman.dla.walk

import com.badlogic.gdx.math.MathUtils.*
import io.github.thanosfisherman.dla.Noise
import io.github.thanosfisherman.dla.Particle

private const val timescale = 0.2f

class PerlinNoiseWalkStrategy(private val width: Float, private val height: Float) : WalkStrategy {
    override fun walk(particle: Particle) {

        var deltaX = particle.x
        var deltaY = particle.y

        val pls = Noise.noise(deltaX * 0.01f, deltaY * 0.01f) * PI * 2

        deltaX += cos(pls) * timescale
        deltaY += sin(pls) * timescale

        particle.x = deltaX
        particle.y = deltaY
    }
}