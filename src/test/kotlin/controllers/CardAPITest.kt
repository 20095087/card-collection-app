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
    inner class AddCards {
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

    @Nested
    inner class UpdateCards {
        @Test
        fun `updating a note that does not exist returns false`() {
            assertFalse(populatedCards!!.updateCard(6, Card("Updating Card", 2, "rare", 2)))
            assertFalse(populatedCards!!.updateCard(-1, Card("Updating Card", 2, "rare", 3)))
            assertFalse(emptyCards!!.updateCard(0, Card("Updating Card", 2, "rare", 4)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(baseball, populatedCards!!.findCard(4))
            assertEquals("Barry Bonds", populatedCards!!.findCard(4)!!.name)
            assertEquals(3, populatedCards!!.findCard(4)!!.quality)
            assertEquals("uncommon", populatedCards!!.findCard(4)!!.rarity)

            //update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedCards!!.updateCard(4, Card("Updating Note", 2, "rare",2)))
            assertEquals("Updating Note", populatedCards!!.findCard(4)!!.name)
            assertEquals(2, populatedCards!!.findCard(4)!!.quality)
            assertEquals("rare", populatedCards!!.findCard(4)!!.rarity)
        }
    }

    @Nested
    inner class ListCards {

        @Test
        fun `listAllCards returns No Cards Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCards!!.numberOfCards())
            assertTrue(emptyCards!!.listAllCards().lowercase().contains("no cards"))
        }

        @Test
        fun `listAllCards returns Cards when ArrayList has cards stored`() {
            assertEquals(5, populatedCards!!.numberOfCards())
            val cardsString = populatedCards!!.listAllCards().lowercase()
            assertTrue(cardsString.contains("ronaldo"))
            assertTrue(cardsString.contains("kobe"))
            assertTrue(cardsString.contains("pikachu"))
            assertTrue(cardsString.contains("sidney crosby"))
            assertTrue(cardsString.contains("barry bonds"))
        }

        @Test
        fun `listByName returns No cards when ArrayList is empty`() {
            assertEquals(0, emptyCards!!.numberOfCards())
            assertTrue(emptyCards!!.searchByName("").lowercase().contains("")
            )
        }

        @Test
        fun `listByName returns no cards when no cards of that name exist`() {
            assertEquals(5, populatedCards!!.numberOfCards())
            val nameToString = populatedCards!!.searchByName("awdaw").lowercase()
            assertFalse(nameToString.contains("awdaw"))
        }

        @Test
        fun `listByName returns all card that match that name when cards of that name exist`() {
            assertEquals(5, populatedCards!!.numberOfCards())
            val nameToString2 = populatedCards!!.searchByName("Ronaldo").lowercase()
            assertTrue(nameToString2.contains("r"))
            assertTrue(nameToString2.contains("aldo"))
            assertFalse(nameToString2.contains("bob"))
            assertFalse(nameToString2.contains("john"))
            assertFalse(nameToString2.contains("jack"))

            val nameToString3 = populatedCards!!.searchByName("Pikachu").lowercase(Locale.getDefault())
            assertTrue(nameToString3.contains("achu"))
            assertFalse(nameToString3.contains("bob"))
            assertTrue(nameToString3.contains("p"))
            assertFalse(nameToString3.contains("john"))
        }

        @Test
        fun `list card by quality returns no cards when no cards with that quality exist`(){
            // searching a populated collection for a quality that does not exist
            assertEquals(5, populatedCards!!.numberOfCards())
            val quality2String = populatedCards!!.listByQuality(3).lowercase()
            assertFalse(quality2String.contains("2"))
        }

        @Test
        fun `list cards by quality returns cards when card with that quality exist`(){
            assertEquals(5,populatedCards!!.numberOfCards())

            // searching a populated collection for quality that exists
            var searchResults = populatedCards!!.listByQuality(9)
            assertTrue(searchResults.contains("9"))
            assertFalse(searchResults.contains("7"))

        }
    }

    @Nested
    inner class DeleteCards {
        @Test
        fun `deleting a Card that does not exist, returns null`() {
            assertNull(emptyCards!!.deleteCard(0))
            assertNull(populatedCards!!.deleteCard(-1))
            assertNull(populatedCards!!.deleteCard(5))
        }

        @Test
        fun `deleting a card that exists delete and returns deleted object`() {
            assertEquals(5, populatedCards!!.numberOfCards())
            assertEquals(baseball, populatedCards!!.deleteCard(4))
            assertEquals(4, populatedCards!!.numberOfCards())
            assertEquals(soccer, populatedCards!!.deleteCard(0))
            assertEquals(3, populatedCards!!.numberOfCards())
        }
    }

    @Nested
    inner class SearchMethods{
        @Test
        fun `search card by rarity returns no cards when no cards with that rarity exist`(){
            // searching a populated collection for a rarity that does not exist
            assertEquals(5,populatedCards!!.numberOfCards())
            val searchResults = populatedCards!!.listByRarity("no result expected")
            assertTrue(searchResults.isEmpty())

            // searching an empty collection
            assertEquals(0,emptyCards!!.numberOfCards())
            assertTrue(emptyCards!!.listByRarity("").isEmpty())
        }

        @Test
        fun `search cards by rarity returns cards when card with that rarity exist`(){
            assertEquals(5,populatedCards!!.numberOfCards())

            // searching a populated collection for rarity that exists
            var searchResults = populatedCards!!.listByRarity("rare")
            assertTrue(searchResults.contains("rare"))
            assertFalse(searchResults.contains("ultra rare"))

            // searching a populated collection for a rarity that case don't match
            searchResults = populatedCards!!.listByRarity("rARe")
            assertTrue(searchResults.contains("rare"))
            assertFalse(searchResults.contains("uncommon"))
        }

        @Test
        fun `search cards by name returns no cards when no cards with that name exist`(){
            // searching a populated collection for a name that does not exist
            assertEquals(5,populatedCards!!.numberOfCards())
            val searchResults = populatedCards!!.searchByName("no result expected")
            assertTrue(searchResults.isEmpty())

            // searching an empty collection
            assertEquals(0,emptyCards!!.numberOfCards())
            assertTrue(emptyCards!!.searchByName("").isEmpty())
        }

        @Test
        fun `search cards by name returns cards when cards with that name exist`(){
            assertEquals(5,populatedCards!!.numberOfCards())

            // searching a populated collection for a full name that exists
            var searchResults = populatedCards!!.searchByName("Kobe")
            assertTrue(searchResults.contains("Kobe"))
            assertFalse(searchResults.contains("Bob"))

            // searching a populated collection for a partial name that exists
            searchResults = populatedCards!!.searchByName("Ron")
            assertTrue(searchResults.contains("Ronaldo"))
            assertFalse(searchResults.contains("Bob"))

            // searching a populated collection for a partial name that exists but case dont match
            searchResults = populatedCards!!.searchByName("piKa")
            assertTrue(searchResults.contains("Pikachu"))
            assertFalse(searchResults.contains("pokachoo"))
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfCardsCalculatedCorrectly() {
            assertEquals(5, populatedCards!!.numberOfCards())
            assertEquals(0, emptyCards!!.numberOfCards())
        }

        @Test
        fun numberOfCardsByRarity() {
            assertEquals(1, populatedCards!!.numberOfCommons())
            assertEquals(0, emptyCards!!.numberOfCommons())

            assertEquals(1, populatedCards!!.numberOfUncommons())
            assertEquals(0, emptyCards!!.numberOfUncommons())

            assertEquals(2, populatedCards!!.numberOfRares())
            assertEquals(0, emptyCards!!.numberOfRares())

            assertEquals(0, populatedCards!!.numberOfVeryRares())
            assertEquals(0, emptyCards!!.numberOfVeryRares())

            assertEquals(1, populatedCards!!.numberOfUltraRares())
            assertEquals(0, emptyCards!!.numberOfUltraRares())
        }
    }

    @Test
    fun `saving and loading an empty collection in XML doesn't crash app`() {
        // Saving an empty cards.xml file.
        val storingCards = CardAPI(XMLSerializer(File("cards.xml")))
        storingCards.store()

        //Loading the empty cards.xml file into a new object
        val loadedCards = CardAPI(XMLSerializer(File("cards.xml")))
        loadedCards.load()

        //Comparing the source of the cards (storingCards) with the xml loaded cards (loadedCards)
        assertEquals(0, storingCards.numberOfCards())
        assertEquals(0, loadedCards.numberOfCards())
        assertEquals(storingCards.numberOfCards(), loadedCards.numberOfCards())
    }

    @Test
    fun `saving and loading an loaded collection in XML doesn't loose data`() {
        // Storing 3 cards to the cards.xml file.
        val storingCards = CardAPI(XMLSerializer(File("cards.xml")))
        storingCards.add(soccer!!)
        storingCards.add(basketball!!)
        storingCards.add(pokemon!!)
        storingCards.store()

        //Loading cards.xml into a different collection
        val loadedCards = CardAPI(XMLSerializer(File("cards.xml")))
        loadedCards.load()

        //Comparing the source of the cards (storingCards) with the xml loaded cards (loadedCards)
        assertEquals(3, storingCards.numberOfCards())
        assertEquals(3, loadedCards.numberOfCards())
        assertEquals(storingCards.numberOfCards(), loadedCards.numberOfCards())
        assertEquals(storingCards.findCard(0), loadedCards.findCard(0))
        assertEquals(storingCards.findCard(1), loadedCards.findCard(1))
        assertEquals(storingCards.findCard(2), loadedCards.findCard(2))
    }
}