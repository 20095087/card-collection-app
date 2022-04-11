package controllers

import models.Card
import persistence.Serializer

class CardAPI (serializerType: Serializer){
    private var cards = ArrayList<Card>()

    fun add(card: Card): Boolean{
        return cards.add(card)
    }
}