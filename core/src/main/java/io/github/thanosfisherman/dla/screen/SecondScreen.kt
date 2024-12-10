package io.github.thanosfisherman.dla.screen

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
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

    //private val vectorMouse = vec3()

    private val vector30: Vector2 = vec2(1f, 0f)

    private var simStarted = false
    private lateinit var snowflake: Snowflake
    private lateinit var screenShot: HighResScreenshot

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

        if (!simStarted) {
            return
        }

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
            it.rectLine(
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
            )

            snowflake.draw(it)
//            val newpart = screenCenter.cpy().add(vectorPart)
//            it.circle(newpart.x, newpart.y, 8f)

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
}