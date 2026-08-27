import org.teavm.gradle.api.OptimizationLevel

plugins {
    `base-plugin-kotlin`
    id("com.github.xpenatan.gdx-teavm") version Versions.Libgdx.gdxTeaVMVersion

}

group = "io.github.thanosfisherman.dla.teavm"
version = "1.1.0"

dependencies {
    addTeaVMDependencies()
}

gdxTeaVM {
    assets(rootProject.file("assets"))
    reflection("com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator")

    // Shared by every JS and Wasm target in this module.
    webDefaults {
        mainClass = "io.github.thanosfisherman.dla.teavm.TeaVMLauncher"
        htmlTitle = "My Game"
        htmlWidth = 1280
        htmlHeight = 720
        obfuscated = false
    }

    // Unnamed development targets keep the original task names.
    js {
        serverPort = 8088
        devServer {
            enabled = true
            autoBuild = true
            autoReload = true
        }
    }

    wasm {
        serverPort = 8089
        outOfProcess = true
        processMemory = 1024
    }

    // Named release targets have independent properties, output, and tasks.
    js("release") {
        optimization = OptimizationLevel.BALANCED
        obfuscated = true
        serverPort = 8180
    }

    wasm("release") {
        optimization = OptimizationLevel.BALANCED
        obfuscated = true
        outOfProcess = true
        processMemory = 2048
        serverPort = 8181
    }
}
val buildJavaScript = tasks.register<JavaExec>("buildGdxTeaVM") {
    val mainClassName = "io.github.thanosfisherman.dla.teavm.TeaVMBuilder"
    dependsOn(tasks.classes)
    description = "Transpile bytecode to JavaScript via TeaVM"
    mainClass.set(mainClassName)
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.build.configure { dependsOn(buildJavaScript) }