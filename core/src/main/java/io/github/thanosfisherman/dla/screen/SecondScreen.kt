package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.thanosfisherman.dla.FrameRate
import io.github.thanosfisherman.dla.HighResScreenshot
import io.github.thanosfisherman.dla.snowflake.Snowflake
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import ktx.math.vec2


class SecondScreen : KtxScreen {
    private val gameViewport = ScreenViewport()
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    private val shape = ShapeRenderer()
    private val fps = FrameRate()
    private val camera = gameViewport.camera as OrthographicCamera

    //private val vectorMouse = vec3()

    private val vector30: Vector2 = vec2(1f, 0f)

    private var simStarted = false
    private lateinit var snowflake: Snowflake
    private lateinit var screenShot: HighResScreenshot
    private val rotationSpeed = 0.5f

//    private val vector = vec3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())


    override fun show() {

        screenShot = HighResScreenshot(shape, gameViewport.worldWidth.toInt(), gameViewport.worldHeight.toInt())

        Gdx.input.inputProcessor = object : KtxInputAdapter {

            override fun keyUp(keycode: Int): Boolean {

                when (keycode) {
                    Keys.SPACE -> simStarted = !simStarted
                    Keys.D -> {
                        fps.isRendered = !fps.isRendered
                    }

                    Keys.N -> {
                        snowflake = Snowflake(gameViewport.worldWidth, gameViewport.worldHeight)
                    }

                    Keys.S -> {
                        if (Gdx.app.type == Application.ApplicationType.Desktop) {
                            screenShot.takeScreenshot()
                        }
                    }
                }
                return true
            }

            override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                simStarted = !simStarted
                return true
            }
        }

        vector30.setAngleDeg(30f)
        vector30.setLength(1000f)

        snowflake = Snowflake(gameViewport.worldWidth, gameViewport.worldHeight)
        gameViewport.camera.position.set(0f, 0f, 0f)
        gameViewport.apply()
    }

    override fun render(delta: Float) {
        super.render(delta)
        clearScreen(red = 0.0f, green = 0.0f, blue = 0.0f)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        if (!simStarted) {
            return
        }

        handleInput()
        fps.update(delta)

        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()

        gameViewport.apply()

//        vectorPart.x -= 0.8f
//        vectorPart.y += Juniper.random.nextFloat(-10f, 10f)
//        val angle = vectorPart.angleRad()
//        vectorPart.constrainAngleRad(angle, 0f, MathUtils.PI / 6)

        snowflake.update()
        shape.use(ShapeRenderer.ShapeType.Filled, gameViewport.camera.combined) {
            /*          it.rectLine(
                          0f,
                          0f,
                          gameViewport.worldWidth,
                          0f,
                          5f,
                          Color.RED,
                          Color.RED,
                      )

                      it.rectLine(
                          0f,
                          0f,
                          vector30.x,
                          vector30.y,
                          5f,
                          Color.GREEN,
                          Color.GREEN,
                      )*/

            snowflake.draw(it)
        }

        //vectorMouse.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        //gameViewport.camera.unproject(vectorMouse)
        //Gdx.graphics.setTitle("DEBUG - X: ${vectorMouse.x} Y: ${vectorMouse.y}")

        uiViewport.apply()
        batch.use(uiViewport.camera.combined) {
            fps.render(it, 0, 0, 0f, uiViewport.worldHeight)
        }
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, false)
        uiViewport.update(width, height, true)
    }

    override fun dispose() {
        batch.disposeSafely()
        fps.disposeSafely()
        shape.disposeSafely()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02.toFloat()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02.toFloat()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-3f, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(3f, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0f, -3f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0f, 3f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.rotate(-rotationSpeed, 0f, 0f, 1f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.rotate(rotationSpeed, 0f, 0f, 1f)
        }
    }
}