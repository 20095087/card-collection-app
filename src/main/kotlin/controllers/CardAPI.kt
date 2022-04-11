package controllers

import models.Card
import persistence.Serializer

class CardAPI (serializerType: Serializer){
    // creating an arraylist of cards
    private var cards = ArrayList<Card>()
    // getting the serializer class
    private var serializer: Serializer = serializerType

    // function for adding cards
    fun add(card: Card): Boolean{
        return cards.add(card)
    }

    // this function lists all cards
    fun listAllCards(): String =
        if(cards.isEmpty()) "No cards stored"
        else formatListString(cards)

    // this function formats the cards
    // we created this function, so we would not repeat ourselves
    fun formatListString(cardsToFormat : List<Card>) : String =
        cardsToFormat
            .joinToString (separator = "\n") { card ->
                cards.indexOf(card).toString() + ": " + card.toString() }

    // returns the amount of cards.
    fun numberOfCards(): Int {
        return cards.size
    }

    fun updateCard(indexToUpdate: Int, note: Card?): Boolean {
        //find the note object by the index number
        val foundNote = findCard(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.name = note.name
            foundNote.cardNum = note.cardNum
            foundNote.rarity = note.rarity
            foundNote.quality = note.quality
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }



    // this function loads the cards
    @Throws(Exception::class)
    fun load() {
        cards = serializer.read() as ArrayList<Card>
    }

    // this function saves the cards ot a file
    @Throws(Exception::class)
    fun store() {
        serializer.write(cards)
    }
}