import utils.ScannerInput
import java.lang.System.exit
import mu.KotlinLogging


// variables
private val logger = KotlinLogging.logger {}


fun main(args: Array<String>) {
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
                 > |   1) Add Album                      |
                 > |   2) Update Album                   |
                 > |   3) Delete Album                   |
                 > |   4) List all Albums                |
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
            1 -> addAlbum()
            // run updateAlbum function
            2 -> updateAlbum()
            // run deleteAlbum function
            3 -> deleteAlbum()
            // run listAlbums function
            4 -> listAlbums()
        }
    }while (true)
}

// this function will allow the user to add a new album
fun addAlbum(){
    logger.info{"addAlbum() function invoked"}
}

// this function will allow the use rot update an existing album
fun updateAlbum(){
    logger.info{"updateAlbum() function invoked"}
}

// this function will allow the user to delete an existing album
// and all the cards inside of it
fun deleteAlbum(){
    logger.info{"deleteAlbum() function invoked"}
}

// this function will allow the user to display all the exisitng albums
fun listAlbums(){
    logger.info{"listAlbums() function invoked"}
}

// this function allows the suer to exit the app
fun exitApp(){
    logger.info{"exitApp() function invoked"}
    exit(0)
}