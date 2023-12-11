package com.golfpvcc.memory.feature.util

import com.golfpvcc.memory.feature.model.MemoryCard

fun generatCardsArray(matched: Int): Array<MemoryCard> {
    val singles = 1..matched
    val doubles = singles + singles
    return doubles.shuffled().map { MemoryCard(it) }.toTypedArray()
}