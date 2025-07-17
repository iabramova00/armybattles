package softserve.academy

import java.util.ArrayDeque

class Army {
    private val units = ArrayDeque<Warrior>()

    val isDefeated: Boolean
        get() = units.isEmpty()

    private fun peekNextFighter(): Warrior? = units.peek()

    private fun removeFallenFighter() {
        units.poll()
    }

    fun addUnits(factory: () -> Warrior, count: Int) {
        require(count > 0) { "Must add a positive number of units." }
        repeat(count) {
            units.add(factory())
        }
    }

    fun attack(enemyArmy: Army): Boolean {
        val ourFighter = this.peekNextFighter()
        val enemyFighter = enemyArmy.peekNextFighter()

        if (ourFighter == null || enemyFighter == null) {
            return ourFighter != null
        }

        val weWonTheDuel = fight(ourFighter, enemyFighter)

        if (weWonTheDuel) {
            enemyArmy.removeFallenFighter()
        } else {
            this.removeFallenFighter()
        }

        return weWonTheDuel
    }
}

fun fight(army1: Army, army2: Army): Boolean {
    while (!army1.isDefeated && !army2.isDefeated) {
        army1.attack(army2)
    }

    return !army1.isDefeated
}