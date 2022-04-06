import utils.ScannerInput
import java.lang.System.exit

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

fun runMenu(){
    do {
        val option = mainMenu()
        when(option){
            0 -> exit(0)
        }
    }while (true)
}