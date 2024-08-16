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
    var hideWalkers = true

    init {
        seedStrategy.seed(cluster)
    }

    fun walkerCount() = cluster.walkers.size
    fun treeCount() = cluster.dendrite.size

    fun update() {
        val fps = Gdx.graphics.framesPerSecond
        val refreshRate = Gdx.graphics.displayMode.refreshRate

        if (cluster.walkers.size < MAX_WALKERS * fps / refreshRate) {
            repeat(100) {
                cluster.addWalker(spawnStrategy.spawn(cluster))
            }
        }

        for (index in 0 until Config.ITERATIONS) {
            for (i in cluster.walkers.size - 1 downTo 0) {
                val walker = cluster.walkers[i]
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
        if (!hideWalkers) {
            cluster.walkers.forEach {
                it.draw(shapeRenderer, Color.BLUE)
            }
        }
        cluster.dendrite.forEach {
            it.draw(shapeRenderer, dendriteColor(it))
        }
        debug(shapeRenderer)
    }

    //TODO: maybe use flyweight pattern to share same instances of Color?
    private fun dendriteColor(particle: Particle): Color {
        val hu = particle.lifeTime * 0.00006f
        val blue = hu * 0.4f
        val green = hu * 0.2f
        val red = 0.25f
        return Color(red, green, blue, 1f)
    }

    private val debugColor = Color(1.0f, 0.0f, 0.0f, 0.4f)
    private fun debug(shapeRenderer: ShapeRenderer) {
        if (isDebug) {
            cluster.bottomLeft.draw(shapeRenderer)
            cluster.topRight.draw(shapeRenderer)
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
