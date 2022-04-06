import utils.ScannerInput

fun main(args: Array<String>) {

}

fun MainMenu(): Int {
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
    )
}