package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.GL20
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
//    private val vector = vec3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())


    override fun show() {
        Gdx.input.inputProcessor = object : KtxInputAdapter {

            override fun keyUp(keycode: Int): Boolean {

                if (keycode == Keys.SPACE) {
                    simStarted = !simStarted
                }

                if (keycode == Keys.F2) {
                    fps.isRendered = !fps.isRendered
                    simulation.isDebug = !simulation.isDebug
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
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        if (!simStarted) return

        fps.update(delta)
        simulation.update()

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        gameViewport.apply()
        shape.projectionMatrix = gameViewport.camera.combined
        shape.begin(ShapeRenderer.ShapeType.Filled)
        simulation.draw(shape)
        shape.end()

//        vector.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
//        gameViewport.camera.unproject(vector)
//        Gdx.graphics.setTitle("DEBUG - X: ${vector.x} Y: ${vector.y}")

        uiViewport.apply()
        batch.use(uiViewport.camera.combined) {
            fps.render(it, 0f, uiViewport.worldHeight)
        }
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }

    override fun dispose() {
        batch.disposeSafely()
        fps.disposeSafely()
        shape.disposeSafely()
    }
}