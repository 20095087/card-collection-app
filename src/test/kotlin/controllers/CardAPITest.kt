package controllers

import models.Card
import org.junit.jupiter.api.*
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
        soccer = Card("Ronaldo", 5, "rare", 6)
        basketball = Card("Kobe", 99, "ultra rare",9,)
        pokemon = Card("Pikachu", 4, "common", 8)
        hockey = Card("Sidney Crosby", 66, "rare", 7)
        baseball = Card("Barry Bonds", 34, "uncommon", 3)

        //adding 5 Note to the notes api
        populatedCards!!.add(soccer!!)
        populatedCards!!.add(basketball!!)
        populatedCards!!.add(pokemon!!)
        populatedCards!!.add(hockey!!)
        populatedCards!!.add(baseball!!)
    }
}