import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import softserve.academy.Knight
import softserve.academy.Warrior
import softserve.academy.fight

class FightTest {

    @Test
    @DisplayName("1. Warrior loses when fighting a Knight")
    fun `warrior should lose against a knight`() {
        // given
        val carl = Warrior()
        val jim = Knight()

        // when
        val result = fight(carl, jim)

        // then
        assertEquals(false, result, "A Warrior should not win against a Knight.")
    }

    @Test
    @DisplayName("2. Knight wins when fighting a Warrior")
    fun `knight should win against a warrior`() {
        // given
        val ramon = Knight()
        val slevin = Warrior()

        // when
        val result = fight(ramon, slevin)

        // then
        assertEquals(true, result, "A Knight should win against a Warrior.")
    }

    @Test
    @DisplayName("3. First of two Warriors wins the fight")
    fun `first warrior should be alive after fighting another warrior`() {
        // given
        val bob = Warrior()
        val mars = Warrior()

        // when
        fight(bob, mars)

        // then
        assertEquals(true, bob.isAlive, "The first warrior to attack should survive a duel with another warrior.")
    }

    @Test
    @DisplayName("4. Knight survives a fight with a Warrior")
    fun `knight should be alive after fighting a warrior`() {
        // given
        val zeus = Knight()
        val godkiller = Warrior()

        // when
        fight(zeus, godkiller)

        // then
        assertEquals(true, zeus.isAlive, "A Knight should survive a duel with a Warrior.")
    }

    @Test
    @DisplayName("5. Second of two Warriors loses the fight")
    fun `second warrior should not be alive after fighting another warrior`() {
        // given
        val husband = Warrior()
        val wife = Warrior()

        // when
        fight(husband, wife)

        // then
        assertEquals(false, wife.isAlive, "The second warrior should not survive a duel with another warrior.")
    }

    @Test
    @DisplayName("6. Knight survives being attacked by a Warrior")
    fun `knight should still be alive after being attacked by a warrior`() {
        // given
        val dragon = Warrior() // The attacker
        val knight = Knight()  // The defender

        // when
        fight(dragon, knight)

        // then
        assertEquals(true, knight.isAlive, "The knight should survive the duel even when attacked first.")
    }

    @Test
    @DisplayName("7. Wounded Knight loses to a fresh Warrior")
    fun `a wounded knight should lose to a fresh warrior`() {
        // given
        val unit1 = Warrior()
        val unit2 = Knight() // This knight will get wounded
        val unit3 = Warrior() // This warrior is fresh

        // when
        fight(unit1, unit2)
        val result = fight(unit2, unit3)

        // then
        assertEquals(false, result, "The wounded knight should lose the second fight.")
    }
}
