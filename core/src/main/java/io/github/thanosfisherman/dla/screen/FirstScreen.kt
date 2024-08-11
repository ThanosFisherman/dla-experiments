package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.thanosfisherman.dla.FrameRate
import io.github.thanosfisherman.dla.Simulation
import io.github.thanosfisherman.dla.seed.CenterInitialSeedStrategy
import io.github.thanosfisherman.dla.spawn.RandomSpawnStrategy
import io.github.thanosfisherman.dla.walk.RandomWalkStrategy
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use

class FirstScreen : KtxScreen {
    private val gameViewport = FitViewport(900f, 900f)
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    private val shape = ShapeRenderer()
    private val fps = FrameRate()
    private lateinit var simulation: Simulation
    private var simStarted = false


    override fun show() {
        Gdx.input.inputProcessor = object : KtxInputAdapter {

            override fun keyUp(keycode: Int): Boolean {

                if (keycode == Keys.SPACE) {
                    simStarted = !simStarted
                }
                return true
            }
        }
        simulation = Simulation(
            gameViewport.worldWidth,
            gameViewport.worldHeight,
            RandomWalkStrategy(gameViewport.worldWidth, gameViewport.worldHeight),
            CenterInitialSeedStrategy(),
            RandomSpawnStrategy()
        )
    }

    override fun render(delta: Float) {
        super.render(delta)
        clearScreen(red = 0.0f, green = 0.0f, blue = 0.0f)
        if (!simStarted) return

        fps.update(delta)
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        gameViewport.apply()
        shape.projectionMatrix = gameViewport.camera.combined
        simulation.update()
        shape.begin(ShapeRenderer.ShapeType.Filled)
        simulation.draw(shape)
        shape.end()

        uiViewport.apply()
        batch.projectionMatrix = uiViewport.camera.combined
        batch.use {
            fps.render(it, height = uiViewport.worldHeight)
        }
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }

    override fun dispose() {
        batch.disposeSafely()
        fps.dispose()
        shape.dispose()
    }
}