package controllers

import models.Card
import persistence.Serializer

class CardAPI (serializerType: Serializer){
    // creating an arraylist of cards
    private var cards = ArrayList<Card>()
    // getting the serializer class
    private var serializer: Serializer = serializerType

    // funciton for adding cards
    fun add(card: Card): Boolean{
        return cards.add(card)
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