package softserve.academy

interface Defendable {
    val defense: Int
}

open class Warrior(
    initialHealth: Int = 50,
    val attack: Int = 5
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
    initialHealth = 50,
    attack = 7
)

class Defender : Warrior(
    initialHealth = 60,
    attack = 3
), Defendable {
    override val defense: Int = 2
}

fun fight(warrior1: Warrior, warrior2: Warrior): Boolean {
    while (warrior1.isAlive && warrior2.isAlive) {
        val defenseOfWarrior2 = if (warrior2 is Defendable) warrior2.defense else 0
        val damageToWarrior2 = warrior1.attack - defenseOfWarrior2
        warrior2.takeDamage(damageToWarrior2)

        if (warrior2.isAlive) {
            val defenseOfWarrior1 = if (warrior1 is Defendable) warrior1.defense else 0
            val damageToWarrior1 = warrior2.attack - defenseOfWarrior1
            warrior1.takeDamage(damageToWarrior1)
        }

        if (warrior1.attack == 0 && warrior2.attack == 0) {
            return false
        }
    }

    return warrior1.isAlive
}