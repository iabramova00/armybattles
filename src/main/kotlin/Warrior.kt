package softserve.academy

open class Warrior(
    initialHealth: Int = 50, val attack: Int = 5
) {
    private var _health: Int = initialHealth
    val health: Int
        get() = _health

    val isAlive: Boolean
        get() = _health > 0

    init {
        require(attack >= 0) { "Attack value cannot be negative." }
    }

    fun takeDamage(damage: Int) {
        if (damage > 0) {
            this._health -= damage
        }
    }
}

class Knight : Warrior(
    initialHealth = 50, // Adheres to the base health value.
    attack = 7
)

fun fight(warrior1: Warrior, warrior2: Warrior): Boolean {
    while (warrior1.isAlive && warrior2.isAlive) {
        warrior2.takeDamage(warrior1.attack)

        if (warrior2.isAlive) {
            warrior1.takeDamage(warrior2.attack)
        }

        if (warrior1.attack == 0 && warrior2.attack == 0) {
            return false
        }
    }

    return warrior1.isAlive
}

