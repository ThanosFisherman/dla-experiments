package io.github.thanosfisherman.dla

import org.junit.jupiter.api.Test

class ClusterTest {

    val cols = 3
    val rows = 3

    val arraylist = ArrayList<Int>()

    val treeRev: Array<Array<ArrayList<Int>>> = Array(rows) { Array(cols) { ArrayList() } }

    @Test
    fun test2DArray() {

        var value = 1

        for (i in treeRev.indices) {
            for (j in treeRev[i].indices) {
                treeRev[i][j].add(value++)
            }
        }

        for (i in treeRev.indices) {
            for (j in treeRev[i].indices) {
                print(treeRev[i][j])
            }
            println()
        }
    }
}

data class Cell(val walkers: ArrayList<Int>)