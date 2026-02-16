package io.github.thanosfisherman.dla.seed

import io.github.thanosfisherman.dla.dlaalgo.Cluster

interface InitialSeedStrategy {

    fun seed(cluster: Cluster)
}