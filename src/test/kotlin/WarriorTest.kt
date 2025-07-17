package softserve.academy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Duration

@DisplayName("Warrior Class and Duel Tests")
class WarriorTest {

    @Nested
    @DisplayName("Initialization and Validation")
    inner class InitializationTests {

        @Test
        fun `default warrior should have 50 health and 5 attack`() {
            val warrior = Warrior()
            assertEquals(50, warrior.health)
            assertEquals(5, warrior.attack)
        }

        @Test
        fun `knight should have 50 health and 7 attack`() {
            val knight = Knight()
            assertEquals(50, knight.health)
            assertEquals(7, knight.attack)
        }

        @Test
        fun `warrior can be created with custom stats`() {
            val customWarrior = Warrior(initialHealth = 100, attack = 10)
            assertEquals(100, customWarrior.health)
            assertEquals(10, customWarrior.attack)
        }

        @Test
        fun `creating a warrior with negative attack should throw IllegalArgumentException`() {
            assertThrows<IllegalArgumentException> {
                Warrior(attack = -1)
            }
        }
    }

    @Nested
    @DisplayName("State and Behavior")
    inner class StateAndBehaviorTests {

        @Test
        fun `isAlive should be true when health is positive`() {
            val warrior = Warrior(initialHealth = 1)
            assertTrue(warrior.isAlive)
        }

        @Test
        fun `isAlive should be false when health is zero`() {
            val warrior = Warrior(initialHealth = 0)
            assertFalse(warrior.isAlive)
        }

        @Test
        fun `isAlive should be false when health is negative`() {
            val warrior = Warrior(initialHealth = -10)
            assertFalse(warrior.isAlive)
        }

        @Test
        fun `takeDamage should reduce health by a positive amount`() {
            val warrior = Warrior()
            warrior.takeDamage(10)
            assertEquals(40, warrior.health)
        }

        @Test
        fun `takeDamage with zero should not change health`() {
            val warrior = Warrior()
            warrior.takeDamage(0)
            assertEquals(50, warrior.health)
        }

        @Test
        fun `takeDamage with a negative value should not change health`() {
            val warrior = Warrior()
            warrior.takeDamage(-10)
            assertEquals(50, warrior.health)
        }

        @Test
        fun `taking damage that exceeds current health results in non-positive health`() {
            val warrior = Warrior(initialHealth = 10)
            warrior.takeDamage(20)
            assertEquals(-10, warrior.health)
            assertFalse(warrior.isAlive)
        }
    }

    @Nested
    @DisplayName("Duel Scenarios")
    inner class DuelScenarios {

        // --- Basic Win/Loss Scenarios (from previous tests) ---

        @Test
        fun `warrior loses when fighting a knight`() {
            val carl = Warrior()
            val jim = Knight()
            val result = fight(carl, jim)
            assertFalse(result, "A Warrior should not win against a Knight.")
        }

        @Test
        fun `knight wins when fighting a warrior`() {
            val ramon = Knight()
            val slevin = Warrior()
            val result = fight(ramon, slevin)
            assertTrue(result, "A Knight should win against a Warrior.")
        }

        @Test
        fun `first of two warriors wins the fight`() {
            val bob = Warrior()
            val mars = Warrior()
            fight(bob, mars)
            assertTrue(bob.isAlive, "The first warrior to attack should survive a duel.")
        }


        @Test
        fun `second of two warriors loses the fight`() {
            val husband = Warrior()
            val wife = Warrior()
            fight(husband, wife)
            assertFalse(wife.isAlive, "The second warrior should not survive a duel.")
        }

        @Test
        fun `wounded knight loses to a fresh warrior`() {
            val unit1 = Warrior()
            val woundedKnight = Knight()
            val unit3 = Warrior()

            fight(unit1, woundedKnight) // Knight gets wounded
            val result = fight(woundedKnight, unit3)

            assertFalse(result, "The wounded knight should lose the second fight.")
        }

        // --- Exhaustive and Edge Case Scenarios ---

        @Test
        fun `duel between two warriors results in correct final health`() {
            val bob = Warrior()
            val mars = Warrior()
            fight(bob, mars)
            assertTrue(bob.isAlive)
            assertEquals(5, bob.health, "The winner should have 5 health remaining.")
            assertFalse(mars.isAlive)
            assertEquals(0, mars.health, "The loser should have 0 health remaining.")
        }

        @Test
        fun `one-shot kill results in no damage to the victor`() {
            val knight = Knight()
            val weakWarrior = Warrior(initialHealth = 7)
            val knightWon = fight(knight, weakWarrior)
            assertTrue(knightWon)
            assertEquals(50, knight.health, "Knight should be at full health.")
            assertFalse(weakWarrior.isAlive)
        }

        @Test
        fun `fight with a dead opponent ends immediately`() {
            val livingWarrior = Warrior()
            val deadWarrior = Warrior(initialHealth = 0)
            val result = fight(livingWarrior, deadWarrior)
            assertTrue(result)
            assertEquals(50, livingWarrior.health, "Living warrior should take no damage.")
        }

        @Test
        fun `stalemate with zero-attack warriors terminates and returns false`() {
            val pacifist1 = Warrior(attack = 0)
            val pacifist2 = Warrior(attack = 0)

            assertTimeoutPreemptively(Duration.ofSeconds(1)) {
                val result = fight(pacifist1, pacifist2)
                assertFalse(result, "A stalemate should result in a loss for the first attacker.")
            }
        }

        @Test
        fun `a warrior fighting itself results in its own demise`() {
            val warrior = Warrior()
            val result = fight(warrior, warrior)
            assertFalse(result)
            assertFalse(warrior.isAlive)
        }
    }
}