package softserve.academy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Army Class and Battle Tests")
class ArmyTest {

    @Nested
    @DisplayName("Army Management")
    inner class ArmyManagementTests {

        @Test
        fun `newly created army should be defeated (empty)`() {
            val army = Army()
            assertTrue(army.isDefeated)
        }

        @Test
        fun `army with units should not be defeated`() {
            val army = Army()
            army.addUnits(::Warrior, 1)
            assertFalse(army.isDefeated)
        }

        @Test
        fun `addUnits should add the correct number of warriors`() {
            val army = Army()
            army.addUnits(::Warrior, 5)
            // To verify the count, we count how many units are defeated
            // when fighting a much stronger army.
            var defeatedUnits = 0
            val strongerDummyArmy = Army().apply { addUnits(::Knight, 100) }

            // We simulate the battle duel by duel.
            while (!army.isDefeated) {
                // The army attacks, and we check if it lost the duel.
                val armyWonTheDuel = army.attack(strongerDummyArmy)
                if (!armyWonTheDuel) {
                    // If our army lost the duel, it means one of its units was defeated.
                    defeatedUnits++
                }
            }
            assertEquals(5, defeatedUnits, "The number of defeated units should match the number added.")
        }

        @Test
        fun `addUnits should add the correct type of warrior`() {
            val army = Army()
            army.addUnits(::Knight, 1)
            val enemyArmy = Army().apply { addUnits(::Warrior, 1) }
            // A knight should win against a single warrior
            val result = fight(army, enemyArmy)
            assertTrue(result)
        }

        @Test
        fun `adding zero or negative units should throw an exception`() {
            val army = Army()
            assertThrows<IllegalArgumentException> {
                army.addUnits(::Warrior, 0)
            }
            assertThrows<IllegalArgumentException> {
                army.addUnits(::Warrior, -5)
            }
        }
    }

    @Nested
    @DisplayName("Basic Battle Scenarios")
    inner class BasicBattleTests {

        @Test
        fun `army of 1 warrior should lose to army of 2 warriors`() {
            val army1 = Army().apply { addUnits(::Warrior, 1) }
            val army2 = Army().apply { addUnits(::Warrior, 2) }
            val result = fight(army1, army2)
            assertFalse(result)
        }

        @Test
        fun `army of 2 warriors should lose to army of 3 warriors`() {
            val army1 = Army().apply { addUnits(::Warrior, 2) }
            val army2 = Army().apply { addUnits(::Warrior, 3) }
            val result = fight(army1, army2)
            assertFalse(result)
        }

        @Test
        fun `army of 5 warriors should lose to army of 7 warriors`() {
            val army1 = Army().apply { addUnits(::Warrior, 5) }
            val army2 = Army().apply { addUnits(::Warrior, 7) }
            val result = fight(army1, army2)
            assertFalse(result)
        }

        @Test
        fun `army of 20 warriors should win against army of 21 warriors`() {
            val army1 = Army().apply { addUnits(::Warrior, 20) }
            val army2 = Army().apply { addUnits(::Warrior, 21) }
            val result = fight(army1, army2)
            assertTrue(result)
        }

        @Test
        fun `army of knights should win against a larger army of warriors`() {
            val knights = Army().apply { addUnits(::Knight, 3) }
            val warriors = Army().apply { addUnits(::Warrior, 4) }
            val result = fight(knights, warriors)
            assertTrue(result)
        }
    }

    @Nested
    @DisplayName("Advanced Battle Scenarios and State Verification")
    inner class AdvancedBattleTests {

        @Test
        fun `surviving warrior should continue fighting with current health`() {
            val army1 = Army().apply { addUnits(::Warrior, 1) }
            val army2 = Army().apply { addUnits(::Warrior, 2) }

            // army1's warrior fights army2's first warrior.
            // army1's warrior loses, army2's warrior survives with 10 health.
            // This is an indirect test, but we can verify the outcome.
            val result = fight(army1, army2)

            assertFalse(result)
            assertTrue(army1.isDefeated)
            assertFalse(army2.isDefeated)
        }

        @Test
        fun `verifying the state of a battle where a single knight loses to two warriors`() {
            // A single knight fights two warriors.
            // Duel 1: Knight (50hp) vs Warrior1 (50hp). Knight wins with 15hp.
            // Duel 2: Wounded Knight (15hp) vs Warrior2 (50hp). The knight loses.
            val knightArmy = Army().apply { addUnits(::Knight, 1) }
            val warriorArmy = Army().apply { addUnits(::Warrior, 2) }

            val knightArmyWon = fight(knightArmy, warriorArmy)

            // The test's primary assertion is corrected: the knight army should lose.
            assertFalse(knightArmyWon, "A single Knight should lose to two sequential Warriors.")
            assertTrue(knightArmy.isDefeated)
            assertFalse(warriorArmy.isDefeated)

            // We can further verify the state of the winning warrior army.
            // The surviving warrior should have 29hp. Let's test this by having it fight a fresh Knight.
            val probeArmy = Army().apply { addUnits(::Knight, 1) } // 50hp, 7 attack
            val survivorWon = fight(warriorArmy, probeArmy)

            // The 29hp warrior will lose to the 50hp knight.
            assertFalse(survivorWon, "The wounded warrior survivor should lose to a fresh knight.")
        }
    }

    @Nested
    @DisplayName("Edge Case Scenarios")
    inner class EdgeCaseTests {

        @Test
        fun `fight between two empty armies should result in a loss for army1`() {
            val army1 = Army()
            val army2 = Army()
            val result = fight(army1, army2)
            assertFalse(result)
        }

        @Test
        fun `fight against an empty army should result in an instant win`() {
            val army1 = Army().apply { addUnits(::Warrior, 1) }
            val emptyArmy = Army()
            val result = fight(army1, emptyArmy)
            assertTrue(result)
        }

        @Test
        fun `fight where army1 is empty should result in an instant loss`() {
            val emptyArmy = Army()
            val army2 = Army().apply { addUnits(::Warrior, 1) }
            val result = fight(emptyArmy, army2)
            assertFalse(result)
        }

        @Test
        fun `fight between two identical armies results in a win for army1 due to first-strike advantage`() {
            val army1 = Army().apply { addUnits(::Warrior, 10) }
            val army2 = Army().apply { addUnits(::Warrior, 10) }
            val result = fight(army1, army2)
            assertTrue(result)
        }
    }
}
