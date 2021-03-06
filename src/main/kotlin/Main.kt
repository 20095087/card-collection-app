import utils.ScannerInput
import java.lang.System.exit
import mu.KotlinLogging
import utils.ScannerInput.readNextLine
import controllers.CardAPI
import models.Card
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.RarityUtility
import utils.ScannerInput.readNextInt
import utils.Utilities
import java.io.File


// variables
private val logger = KotlinLogging.logger {}
//private val cardAPI = CardAPI(XMLSerializer(File("cards.xml")))
private val cardAPI = CardAPI(JSONSerializer(File("cards.json")))

fun main(args: Array<String>) {
    // here we will load the files
    load()
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
                 > |   4) List Cards                     |
                 > ---------------------------------------
                 > |   5) Search by Name                 |
                 > |   6) Number of Cards                |
                 > |   7) Number of Cards by Rarity      |
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
            4 -> listCards()
            // run searchByName function
            5 -> searchByName()
            // run numberOfCards function
            6 -> numberOfCards()
            // run numberOfCardsByRarity function
            7 -> numberOfCardsByRarity()
        }
    }while (true)
}

// this function will allow the user to add a new card
fun addCard(){
    //logger.info{"addCard() function invoked"}
    // getting the user to enter the card name
    val name = readNextLine("Enter card name: ")
    // getting the user to enter the card number
    val cardNum = readNextInt("Enter card number: ")
    // getting the user to enter the rarity of the card
    var rarity = readNextLine("Enter card rarity \n (common, uncommon,rare,very rare, ultra rare): ")
    // validate if the rarity is one of the allowed categories
    while(!RarityUtility.isValidRarity(rarity)) {
        // if the category is correct then break out of the while loop
        if(RarityUtility.isValidRarity(rarity)){
            break
        }
        rarity = readNextLine("Enter card rarity \n (common, uncommon,rare,very rare, ultra rare): ")
    }
    // getting the user to enter card quality between 1-10
    var quality = readNextInt("Enter card quality(0 to 10): ")
    while(!Utilities.validRange(quality,0,10)) {
        // if the quality is in-correct then ask user for input again
        quality = readNextInt("Enter card quality(0 to 10): ")
        // if the quality is within range
        if(Utilities.validRange(quality,0,10)){
            break
        }
    }
    val isAdded = cardAPI.add(Card(name,cardNum,rarity,quality))
    if(isAdded) println("Added Successfully")
    else println("Add Failed")

}

// this function will allow the use rot update an existing card
fun updateCard(){
    //    logger.info { "updateCard() function invoked" }
    listAllCards()
    if (cardAPI.numberOfCards() > 0) {
        //only ask the user to choose the card if notes exist
        val indexToUpdate = readNextInt("Enter the index of the card to update: ")
        // getting the user to enter the card name
        val cardName = readNextLine("Enter new card name: ")
        // get the user to enter the card number of the card
        var cardNum = readNextInt("Enter card number: ")
        // get the user to enter the card rarity
        var rarity = readNextLine("Enter card rarity \n (common, uncommon,rare,very rare, ultra rare): ")
        // validate if the rarity is one of the allowed categories
        while(!RarityUtility.isValidRarity(rarity)) {
            // if the category is correct then break out of the while loop
            if(RarityUtility.isValidRarity(rarity)){
                break
            }
            rarity = readNextLine("Enter card rarity \n (common, uncommon,rare,very rare, ultra rare): ")
        }
        // get the user to enter the status of the note
        var quality = readNextInt("Enter card quality(0 to 10): ")
        // validate if the quality if quality is in range
        while(!Utilities.validRange(quality,0,10)) {
            // if the quality is in-correct then ask user for input again
            quality = readNextInt("Enter card quality(0 to 10): ")
            // if the quality is within range
            if(Utilities.validRange(quality,0,10)){
                break
            }
        }

        //pass the index of the card and the new card details to CardAPI for updating and check for success.
        if (cardAPI.updateCard(indexToUpdate, Card(cardName, cardNum, rarity, quality))){
            println("Update Successful")
        } else {
            println("Update Failed")
        }
    } else {
        println("There are no cards for this index number")
    }
}

// this function will allow the user to delete an existing card
// and all the cards inside it
fun deleteCard(){
    //logger.info{"deleteCard() function invoked"}
    // list the cards
    listAllCards()
    // if there are cards
    if (cardAPI.numberOfCards() > 0) {
        //only ask the user to choose the card to delete if card exist
        val indexToDelete = readNextInt("Enter the index of the card to delete: ")
        //pass the index of the card to CardAPI for deleting and check for success.
        val cardToDelete = cardAPI.deleteCard(indexToDelete)
        if (cardToDelete != null) {
            println("Delete Successful! Deleted card: ${cardToDelete.name}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

// this function will allow the user to display all the existing cards
fun listAllCards(){
    //logger.info{"listCards() function invoked"}
    if(cardAPI.numberOfCards() > 0){
        println(cardAPI.listAllCards())
    }else println("No cards stored")
}

fun listCards() {
    // if there are notes
    if (cardAPI.numberOfCards() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL cards          |
                  > |   2) View by quality         |
                  > |   3) View by rarity          |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllCards();
            2 -> listByQuality();
            3 -> listByRarity();
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No cards stored");
    }
}

// this function is responsible for displaying cards by quality
fun listByQuality(){
    // if there are any cards
    if(cardAPI.numberOfCards() > 0){
        // getting the quality from the user
        val quality = ScannerInput.readNextInt("Enter quality (1-10): ")
        println(cardAPI.listByQuality(quality))
    }
}

// list by rarity function
// this function prints a sub-menu which the user can choose from
fun listByRarity() {
    // if there are cards
    if (cardAPI.numberOfCards() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) common                  |
                  > |   2) uncommon                |
                  > |   3) rare                    |
                  > |   4) very rare               |
                  > |   5) ultra rare              |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> println(cardAPI.listByRarity("common"))
            2 -> println(cardAPI.listByRarity("uncommon"))
            3 -> println(cardAPI.listByRarity("rare"))
            4 -> println(cardAPI.listByRarity("very rare"))
            5 -> println(cardAPI.listByRarity("ultra rare"))
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No cards stored");
    }
}

// this function runs the numberOfCards function from cardAPI and prints it to the console.
fun numberOfCards(){
    println(cardAPI.numberOfCards())
}

// this function runs the numberOfCardsByRarity function from cardAPI and prints it to the console.
fun numberOfCardsByRarity(){
    println(cardAPI.numberOfCardsByRarity())
}

// this function asks the user to enter the name of the card that they want to search for.
// then it prints the searchByName function from cardAPI and passes in the name of the card
fun searchByName(){
    var name = ScannerInput.readNextLine("Enter card name: ")
    println(cardAPI.searchByName(name))
}

// this function calls the store fun from CardAPI
fun save() {
    // exception handling not to crash our app
    try {
        // calls store function from CardAPI
        cardAPI.store()
    } catch (e: Exception) {
        // display exception if one occurs
        System.err.println("Error writing to file: $e")
    }
}

// this function calls the load fun from CardAPI
fun load() {
    // exception handling not to crash our app
    try {
        // calls load function from CardAPI
        cardAPI.load()
    } catch (e: Exception) {
        // display exception if one occurs
        System.err.println("Error reading from file: $e")
    }
}

// this function allows the suer to exit the app
fun exitApp(){
    //logger.info{"exitApp() function invoked"}
    // here we will also save the files
    save()
    exit(0)
}
