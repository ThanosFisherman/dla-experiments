@file:JvmName("TeaVMBuilder")

package io.github.thanosfisherman.dla.teavm

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import org.teavm.vm.TeaVMOptimizationLevel
import java.io.File

/** Builds the TeaVM/HTML application.  */
fun main() {
    val teaBuildConfiguration = TeaBuildConfiguration()
    teaBuildConfiguration.assetsPath.add(AssetFileHandle("../assets"))
    teaBuildConfiguration.webappPath = File("build/dist").getCanonicalPath()
    teaBuildConfiguration.htmlTitle = "Diffusion-limited aggregation by Thanos!"
    teaBuildConfiguration.htmlWidth = 800
    teaBuildConfiguration.htmlHeight = 800

    // Register any extra classpath assets here:
    // teaBuildConfiguration.additionalAssetsClasspathFiles.add("io.github.thanosfisherman.dla/asset.extension");

    // Register any classes or packages that require reflection here:
    // TeaReflectionSupplier.addReflectionClass("io.github.thanosfisherman.dla.reflect");
    val tool = TeaBuilder.config(teaBuildConfiguration)
    tool.optimizationLevel = TeaVMOptimizationLevel.FULL
    tool.setObfuscated(true)
    tool.mainClass = TeaVMLauncher::class.java.getName()
    TeaBuilder.build(tool)
}
