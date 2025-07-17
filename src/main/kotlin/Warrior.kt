package softserve.academy

interface Defendable {
    val defense: Int
}

interface Vampiric {
    val vampirism: Int // Stored as an integer, e.g., 50 for 50%.
}

open class Warrior(
    initialHealth: Int = 50,
    val attack: Int = 5
) {
    private val maxHealth: Int = initialHealth

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

    fun receiveHealing(amount: Int) {
        if (amount > 0) {
            _health = (_health + amount).coerceAtMost(maxHealth)
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

class Vampire : Warrior(
    initialHealth = 40,
    attack = 4
), Vampiric {
    override val vampirism: Int = 50
}

private fun handleAttackTurn(attacker: Warrior, defender: Warrior) {
    val defense = if (defender is Defendable) defender.defense else 0
    val damageDealt = (attacker.attack - defense).coerceAtLeast(0)

    defender.takeDamage(damageDealt)

    if (attacker is Vampiric) {
        val healingAmount = (damageDealt * attacker.vampirism) / 100
        attacker.receiveHealing(healingAmount)
    }
}

fun fight(warrior1: Warrior, warrior2: Warrior): Boolean {
    while (warrior1.isAlive && warrior2.isAlive) {
        handleAttackTurn(attacker = warrior1, defender = warrior2)

        if (warrior2.isAlive) {
            handleAttackTurn(attacker = warrior2, defender = warrior1)
        }

        if (warrior1.attack == 0 && warrior2.attack == 0) {
            return false
        }
    }

    return warrior1.isAlive
}