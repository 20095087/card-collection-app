package utils

object RarityUtility {
    // creating a set of rarities that the user needs to chose from
    @JvmStatic
    val rarities = setOf("common","uncommon","rare","very rare","ultra rare")

    @JvmStatic
    fun isValidRarity(rarityToCheck: String): Boolean{
        for(rarity in rarities){
            if(rarity.equals(rarityToCheck, ignoreCase = true)){
                return true
            }
        }
        return false
    }
}