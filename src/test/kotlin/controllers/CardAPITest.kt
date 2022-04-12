package controllers

import models.Card
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import persistence.XMLSerializer
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class CardAPITest {

    // variables
    //cards
    private var soccer: Card? = null
    private var basketball: Card? = null
    private var pokemon: Card? = null
    private var hockey: Card? = null
    private var baseball: Card? = null
    // storages
    private var populatedCards: CardAPI? = CardAPI(XMLSerializer(File("cards.xml")))
    private var emptyCards: CardAPI? = CardAPI(XMLSerializer(File("cards.xml")))

    @BeforeEach
    fun setup() {
        // assigning values to the cards
        soccer = Card("Ronaldo", 5, "rare", 6)
        basketball = Card("Kobe", 99, "ultra rare",9,)
        pokemon = Card("Pikachu", 4, "common", 8)
        hockey = Card("Sidney Crosby", 66, "rare", 7)
        baseball = Card("Barry Bonds", 34, "uncommon", 3)

        //adding 5 cards to the cards api
        populatedCards!!.add(soccer!!)
        populatedCards!!.add(basketball!!)
        populatedCards!!.add(pokemon!!)
        populatedCards!!.add(hockey!!)
        populatedCards!!.add(baseball!!)
    }

    @AfterEach
    fun tearDown() {
        soccer = null
        basketball = null
        pokemon = null
        hockey = null
        baseball = null
        populatedCards = null
        emptyCards = null
    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Card to a populated list adds to ArrayList`() {
            val newNote = Card("Michael Jordan", 56, "ultra rare", 10)
            assertEquals(5, populatedCards!!.numberOfCards())
            assertTrue(populatedCards!!.add(newNote))
            assertEquals(6, populatedCards!!.numberOfCards())
            assertEquals(newNote, populatedCards!!.findCard(populatedCards!!.numberOfCards() - 1))
        }

        @Test
        fun `adding a Card to an empty list adds to ArrayList`() {
            val newNote = Card("Charizard", 78, "rare", 7)
            assertEquals(0, emptyCards!!.numberOfCards())
            assertTrue(emptyCards!!.add(newNote))
            assertEquals(1, emptyCards!!.numberOfCards())
            assertEquals(newNote, emptyCards!!.findCard(emptyCards!!.numberOfCards() - 1))
        }
    }
}