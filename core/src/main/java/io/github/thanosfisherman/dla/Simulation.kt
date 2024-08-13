package io.github.thanosfisherman.dla

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.thanosfisherman.dla.Config.MAX_WALKERS
import io.github.thanosfisherman.dla.Config.MAX_WALKER_LIFESPAN
import io.github.thanosfisherman.dla.seed.InitialSeedStrategy
import io.github.thanosfisherman.dla.spawn.SpawnStrategy
import io.github.thanosfisherman.dla.walk.WalkStrategy
import ktx.log.logger

private val logger = logger<Simulation>()

class Simulation(
    width: Float,
    height: Float,
    private val walkStrategy: WalkStrategy,
    seedStrategy: InitialSeedStrategy,
    private val spawnStrategy: SpawnStrategy
) {
    private val cluster = Cluster(width, height)
    var isDebug = false

    init {
        seedStrategy.seed(cluster)
    }

    fun update() {
        val fps = Gdx.graphics.framesPerSecond
        val refreshRate = Gdx.graphics.displayMode.refreshRate

        if (cluster.walkers().size < MAX_WALKERS * fps / refreshRate) {
            repeat(100) {
                cluster.addWalker(spawnStrategy.spawn(cluster))
            }
        }

        repeat(Config.ITERATIONS) {
            for (i in cluster.walkers().size - 1 downTo 0) {
                val walker = cluster.walkers()[i]
                walkStrategy.walk(walker).also { walker.lifeTime++ }

                if (cluster.isContained(walker) && cluster.canAttach(walker)) {
                    if (walker.lifeTime > MAX_WALKER_LIFESPAN) {
                        cluster.attach(walker)
                    }
                    cluster.removeWalker(walker)
                }
            }
        }
    }

    fun draw(shapeRenderer: ShapeRenderer) {

        with(shapeRenderer) {
            cluster.walkers().forEach {
                it.draw(this)
            }
            cluster.dendrite().forEach {
                it.draw(this)
            }
            debug(this)
        }
    }

    private val debugColor = Color(1.0f, 0.0f, 0.0f, 0.4f)
    private fun debug(shapeRenderer: ShapeRenderer) {
        if (isDebug) {
            cluster.bottomLeft.draw(shapeRenderer, Color.CYAN)
            cluster.topRight.draw(shapeRenderer, Color.GREEN)
            shapeRenderer.color = debugColor

            shapeRenderer.rect(
                cluster.bottomLeft.x,
                cluster.bottomLeft.y,
                cluster.topRight.x - cluster.bottomLeft.x,
                cluster.topRight.y - cluster.bottomLeft.y
            )
        }
    }
}
