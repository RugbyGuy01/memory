package com.golfpvcc.memory.feature.screen

import com.golfpvcc.memory.feature.model.MemoryCard
import com.golfpvcc.memory.feature.util.Constants.starting_pairs
import com.golfpvcc.memory.feature.util.generatCardsArray
import com.golfpvcc.memory.ui.theme.HolidayTheme
import com.golfpvcc.memory.ui.theme.ThanksgivingTheme

data class MemoryState(
    val cards: Array<MemoryCard> = generatCardsArray(starting_pairs),
    val card1: Int? = null,
    val card2: Int? = null,
    val pairCount: Int = starting_pairs,
    val pairsMatched: Int = 0,
    val clickCount: Int = 0,
    val currentTheme: HolidayTheme = ThanksgivingTheme()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemoryState

        if (!cards.contentEquals(other.cards)) return false
        if (card1 != other.card1) return false
        if (card2 != other.card2) return false
        if (pairCount != other.pairCount) return false
        if (pairsMatched != other.pairsMatched) return false
        if (clickCount != other.clickCount) return false
        return currentTheme == other.currentTheme
    }

    override fun hashCode(): Int {
        var result = cards.contentHashCode()
        result = 31 * result + (card1 ?: 0)
        result = 31 * result + (card2 ?: 0)
        result = 31 * result + pairCount
        result = 31 * result + pairsMatched
        result = 31 * result + clickCount
        result = 31 * result + currentTheme.hashCode()
        return result
    }
}
