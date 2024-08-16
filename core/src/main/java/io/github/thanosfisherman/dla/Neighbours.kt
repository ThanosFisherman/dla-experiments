package io.github.thanosfisherman.dla

object Neighbours {

    fun findNeighbouringCells(
        walker: Particle,
        col: Int,
        row: Int,
        particlesInCells: Array<Array<MutableList<Particle>>>
    ): Boolean {

        val n1 = particlesInCells[col - 1][row - 1]
        val n2 = particlesInCells[col - 1][row]
        val n3 = particlesInCells[col - 1][row + 1]
        val n4 = particlesInCells[col][row - 1]
        val n5 = particlesInCells[col][row]
        val n6 = particlesInCells[col][row + 1]
        val n7 = particlesInCells[col + 1][row - 1]
        val n8 = particlesInCells[col + 1][row]
        val n9 = particlesInCells[col + 1][row + 1]

        n1.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n1.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n2.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n3.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n4.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n5.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n6.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n7.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n8.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        n9.forEach {
            if (calculateDistance(walker, it))
                return true
        }
        return false
    }


    private fun calculateDistance(particle1: Particle, particle2: Particle): Boolean {
        val d2 = particle1.dist2(particle2)
        if (d2 < (particle1.r + particle2.r) * (particle1.r + particle2.r)) {
            return true
        }
        return false
    }
}