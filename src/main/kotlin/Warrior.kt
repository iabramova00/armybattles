package softserve.academy

open class Warrior(
    var health: Int = 50,
    val attack: Int = 5
)

val Warrior.isAlive: Boolean
    get() = health > 0

infix fun Warrior.hits(other: Warrior) {
    other.health -= this.attack
}

class Knight : Warrior(attack = 7)

class Army {
    private val troops = mutableListOf<Warrior>()

    fun addUnit(warrior: Warrior) {
        troops.add(warrior)
    }

    val isAlive: Boolean
        get() = troops.any { it.isAlive }

    val champion: Warrior
        get() = troops.find { it.isAlive }!!

    fun iterator(): Iterator<Warrior> = troops.iterator()
}

fun Army.addUnits(n: Int, factory: () -> Warrior) {
    repeat(n) { addUnit(factory()) }
}

fun fight(first: Warrior, second: Warrior): Boolean {
    while (first.isAlive && second.isAlive) {
        first hits second
        if (second.isAlive) {
            second hits first
        }
    }
    return first.isAlive
}

//fun fight(first: Army, second: Army): Boolean {
//    while (first.isAlive and second.isAlive) {
//        fight(first.champion, second.champion)
//    }
//    return first.isAlive
//}
fun fight(first: Army, second: Army): Boolean {
    val firstIterator = first.iterator()
    if (!firstIterator.hasNext()) return false

    val secondIterator = second.iterator()
    if (!secondIterator.hasNext()) return true

    var firstChampion = firstIterator.next()
    var secondChampion = secondIterator.next()

    while (true) {
        val res = fight(firstChampion, secondChampion)
        if (res) {
            if (!secondIterator.hasNext()) { return true }
            secondChampion = secondIterator.next()
        } else {
            if (!firstIterator.hasNext()) { return false }
            firstChampion = firstIterator.next()
        }
    }
}

fun main() {
    val myArmy = Army()
    myArmy.addUnits(3) { Knight() }

    val enemyArmy = Army()
    enemyArmy.addUnits(3) { Warrior() }

    val army3 = Army()
    army3.addUnits(20) { Warrior() }
    army3.addUnits(5) { Knight() }

    val army4 = Army()
    army4.addUnits(30) { Warrior() }

    check(fight(myArmy, enemyArmy) == true)
    check(fight(army3, army4) == false)
    println("OK")
}
