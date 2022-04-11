import utils.ScannerInput
import java.lang.System.exit
import mu.KotlinLogging
import utils.ScannerInput.readNextLine
import controllers.CardAPI
import models.Card
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import java.io.File


// variables
private val logger = KotlinLogging.logger {}
private val cardAPI = CardAPI(XMLSerializer(File("cards.xml")))

fun main(args: Array<String>) {
    // here we will load the files
    runMenu()
}

fun mainMenu(): Int {
    // return a menu that will be displayed to the user
    // adn get input from the user
    return ScannerInput.readNextInt(
        """
                 > ---------------------------------------
                 > |        CARD COLLECTION APP          |
                 > ---------------------------------------
                 > | MENU                                |
                 > |   1) Add Card                       |
                 > |   2) Update Card                    |
                 > |   3) Delete Card                    |
                 > |   4) List all Cards                 |
                 > ---------------------------------------
                 > |   0) Exit                           |
                 > ---------------------------------------
    ==>>""".trimMargin(">")
    // trimming the margin so the " > " is not visible and menu is
    // formatted correctly
    )
}

// this function is responsible for running functions
// according to user input
fun runMenu(){
    // we are running a do loop so the user can execute many functions
    // and not have to run the app again after each function being executed
    do {
        val option = mainMenu()
        when(option){
            // exit the app
            0 -> exitApp()
            // run addAlbum function
            1 -> addCard()
            // run updateAlbum function
            2 -> updateCard()
            // run deleteAlbum function
            3 -> deleteCard()
            // run listAlbums function
            4 -> listAllCards()
        }
    }while (true)
}

// this function will allow the user to add a new card
fun addCard(){
    //logger.info{"addCard() function invoked"}
    // getting the user to enter the card name
    val cardName = readNextLine("Enter card name: ")
    // getting the user to enter the card number
    val cardNum = readNextInt("Enter card number: ")
    // getting the user to enter the rarity of the card
    val cardRarity = readNextLine("Enter card rarity: ")
    // getting the user to enter card quality between 1-10
    val cardQuality = readNextInt("Enter the card quality: ")

    val isAdded = cardAPI.add(Card(cardName,cardNum,cardRarity,cardQuality))
    if(isAdded) println("Added Successfully")
    else println("Add Failed")

}

// this function will allow the use rot update an existing card
fun updateCard(){
    logger.info{"updateCard() function invoked"}
}

// this function will allow the user to delete an existing card
// and all the cards inside it
fun deleteCard(){
    logger.info{"deleteCard() function invoked"}
}

// this function will allow the user to display all the existing cards
fun listAllCards(){
    //logger.info{"listCards() function invoked"}
    if(cardAPI.numberOfCards() > 0){
        println(cardAPI.listAllCards())
    }else println("No cards stored")
}

// this function allows the suer to exit the app
fun exitApp(){
    logger.info{"exitApp() function invoked"}
    // here we will also save the files
    exit(0)
}