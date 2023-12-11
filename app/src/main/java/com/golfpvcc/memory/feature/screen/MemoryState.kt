package com.golfpvcc.memory.feature.screen

import com.golfpvcc.memory.feature.model.MemoryCard
import com.golfpvcc.memory.feature.util.generatCardsArray
import com.golfpvcc.memory.ui.theme.HolidayTheme
import com.golfpvcc.memory.ui.theme.ThanksgivingTheme

data class MemoryState(
    val cards: Array<MemoryCard> = generatCardsArray(6),
    val card1: Int? = null,
    val card2: Int? = null,
    val pairCount: Int = 6,
    val pairsMatched: Int = 0,
    val clickCount: Int = 0,
    val currentTheme: HolidayTheme = ThanksgivingTheme()
)
