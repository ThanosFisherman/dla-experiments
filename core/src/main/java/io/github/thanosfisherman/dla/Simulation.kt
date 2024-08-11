package io.github.thanosfisherman.dla

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.thanosfisherman.dla.Config.MAX_WALKERS
import io.github.thanosfisherman.dla.seed.InitialSeedStrategy
import io.github.thanosfisherman.dla.spawn.SpawnStrategy
import io.github.thanosfisherman.dla.walk.WalkStrategy
import ktx.log.logger

private val logger = logger<Simulation>()

class Simulation(
    private val width: Float,
    private val height: Float,
    private val walkStrategy: WalkStrategy,
    private val seedStrategy: InitialSeedStrategy,
    private val spawnStrategy: SpawnStrategy
) {
    private val cluster = Cluster(width, height)

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
                walkStrategy.walk(walker)
                if (cluster.isContains(walker) && cluster.canAttach(walker)) {
                    cluster.attach(walker)
                }
            }
        }
    }

    fun draw(shapeRenderer: ShapeRenderer) {
        cluster.walkers().forEach {
            it.draw(shapeRenderer)
        }
        cluster.dendrite().forEach {
            it.draw(shapeRenderer)
        }
        if (true) {
            shapeRenderer.rect(cluster.treeMin.x, cluster.treeMin.y,cluster.treeMax.x-cluster.treeMin.x,cluster.treeMax.y - cluster.treeMin.y)

        }
    }
}