import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import softserve.academy.Army
import softserve.academy.Warrior
import softserve.academy.fight

class BattleTest {
    @Test
    @DisplayName("1. Battle: 1 warrior vs 2 warriors")
    fun `army of 1 warrior should lose to army of 2 warriors`() {
        // given
        val army1 = Army()
        army1.addUnits(::Warrior, 1)
        val army2 = Army()
        army2.addUnits(::Warrior, 2)

        // when
        val result = fight(army1, army2)

        // then
        assertEquals(false, result)
    }

    @Test
    @DisplayName("2. Battle: 2 warriors vs 3 warriors")
    fun `army of 2 warriors should lose to army of 3 warriors`() {
        // given
        val army1 = Army()
        army1.addUnits(::Warrior, 2)
        val army2 = Army()
        army2.addUnits(::Warrior, 3)

        // when
        val result = fight(army1, army2)

        // then
        assertEquals(false, result)
    }

    @Test
    @DisplayName("3. Battle: 5 warriors vs 7 warriors")
    fun `army of 5 warriors should lose to army of 7 warriors`() {
        // given
        val army1 = Army()
        army1.addUnits(::Warrior, 5)
        val army2 = Army()
        army2.addUnits(::Warrior, 7)

        // when
        val result = fight(army1, army2)

        // then
        assertEquals(false, result)
    }

    @Test
    @DisplayName("4. Battle: 20 warriors vs 21 warriors")
    fun `army of 20 warriors should WIN against army of 21 warriors`() { // Renamed for clarity
        // given
        val army1 = Army()
        army1.addUnits(::Warrior, 20)
        val army2 = Army()
        army2.addUnits(::Warrior, 21)

        // when
        val result = fight(army1, army2)

        // then
        assertEquals(true, result)
    }

    @Test
    @DisplayName("5. Battle: 10 warriors vs 11 warriors")
    fun `army of 10 warriors should WIN against army of 11 warriors`() { // Renamed for clarity
        // given
        val army1 = Army()
        army1.addUnits(::Warrior, 10)
        val army2 = Army()
        army2.addUnits(::Warrior, 11)

        // when
        val result = fight(army1, army2)

        // then
        assertEquals(true, result)
    }

    @Test
    @DisplayName("6. Battle: 11 warriors vs 7 warriors")
    fun `army of 11 warriors should win against army of 7 warriors`() {
        // given
        val army1 = Army()
        army1.addUnits(::Warrior, 11)
        val army2 = Army()
        army2.addUnits(::Warrior, 7)

        // when
        val result = fight(army1, army2)

        // then
        assertEquals(true, result)
    }
}