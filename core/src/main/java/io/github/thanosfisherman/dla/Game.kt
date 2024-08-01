package io.github.thanosfisherman.dla

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import io.github.thanosfisherman.dla.screen.FirstScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.logger

private val log = logger<Game>()

class Game : KtxGame<KtxScreen>() {

    override fun create() {
        super.create()
        Gdx.app.logLevel = Application.LOG_DEBUG
        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }

    companion object {
        fun create() = Game()
    }
}

