package io.github.thanosfisherman.dla

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.random
import ktx.log.logger
import ktx.math.vec2

private val logger = logger<Simulation>()

class Simulation(private val width: Float, private val height: Float) {

    private val maxWalkers: Int = 100
    private val iterations = 50
    private val radius = 1f
    private val walkers = mutableListOf<Particle>()
    private val tree = mutableListOf<Particle>().apply {
        add(Particle(radius, vec2(width / 2f, height / 2f)))
    }


    private var hu = 0f
    fun update() {
        while (walkers.size < maxWalkers) {
            val walker = Particle.randomPoint(radius, width, height)
            walkers.add(walker)
        }

        for (n in 0 until iterations) {
            for (i in walkers.size -1 downTo 0) {
                val walker: Particle = walkers[i]
                walker.updateBias(width, height)
                if (walker.checkStuck(tree)) {
                    walker.hu = hu
                    hu += 2
                    tree.add(walker)
                    walkers.removeAt(i)
                }
            }
        }
    }


    fun draw(shapeRenderer: ShapeRenderer) {
        tree.forEach {
            it.draw(shapeRenderer)
        }
//        walkers.forEach {
//            it.draw(shapeRenderer)
//        }
    }
}