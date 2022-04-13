package controllers

import models.Card
import persistence.Serializer
import utils.Utilities

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

    fun updateCard(indexToUpdate: Int, card: Card?): Boolean {
        //find the card object by the index number
        val foundCard = findCard(indexToUpdate)

        //if the note exists, use the card details passed as parameters to update the found card in the ArrayList.
        if ((foundCard != null) && (card != null)) {
            foundCard.name = card.name
            foundCard.cardNum = card.cardNum
            foundCard.rarity = card.rarity
            foundCard.quality = card.quality
            return true
        }

        //if the card was not found, return false, indicating that the update was not successful
        return false
    }

    // this function is responsible for deleting a card
    fun deleteCard(indexToDelete: Int): Card? {
        // we verify if the index passed in is correct and exists
        return if (Utilities.isValidListIndex(indexToDelete, cards)) {
            // finally we remove the card from the arrayList
            cards.removeAt(indexToDelete)
        } else null
    }

    // this function finds the card by its index
    fun findCard(index: Int): Card? {
        return if (Utilities.isValidListIndex(index, cards)) {
            cards[index]
        } else null
    }

    // this function looks through the cards and displays cards with
    // the corresponding rarity
    fun searchByRarity(rarity: String) =
        cards.filter { card -> card.rarity.equals(rarity, ignoreCase = true)}
            .joinToString(separator = "\n") { card -> cards.indexOf(card).toString() + ": " + card.toString()  }

    // this function looks through the cards and displays cards with
    // the corresponding name.
    fun searchByName(name: String) =
        cards.filter { card -> card.name.contains(name, ignoreCase = true)}
            .joinToString(separator = "\n") { card -> cards.indexOf(card).toString() + ": " + card.toString()  }

    // this function looks through the cards and displays cards with
    // the corresponding quality
    fun listByQuality(quality: Int): String =
        if(cards.isEmpty()) "No cards stored."
        else cards.filter { card -> card.quality == quality }.joinToString(separator = "\n") { card -> cards.indexOf(card).toString() + ": " + card.toString() }

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