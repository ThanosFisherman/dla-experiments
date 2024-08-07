package io.github.thanosfisherman.dla.seed

import io.github.thanosfisherman.dla.Cluster

interface InitialSeedStrategy {

    fun seed(cluster: Cluster)
}