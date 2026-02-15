plugins {
    `base-plugin-kotlin`
}

group = "io.github.thanosfisherman.dla.teavm"
version = "1.1.0"

dependencies {
    addTeaVMDependencies()
}

val buildJavaScript = tasks.register<JavaExec>("buildGdxTeaVM") {
    val mainClassName = "io.github.thanosfisherman.dla.teavm.TeaVMBuilder"
    dependsOn(tasks.classes)
    description = "Transpile bytecode to JavaScript via TeaVM"
    mainClass.set(mainClassName)
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.build.configure { dependsOn(buildJavaScript) }