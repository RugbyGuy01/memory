package com.golfpvcc.memory.feature.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golfpvcc.memory.feature.presentation.MemoryEvent
import com.golfpvcc.memory.feature.util.Constants
import com.golfpvcc.memory.feature.util.generatCardsArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MemoryViewModel : ViewModel() {
    private val _state = mutableStateOf(MemoryState())
    val state: State<MemoryState> = _state
    private var delayedCompareJob: Job? = null


    fun onEvent(event: MemoryEvent) {
        when (event) {
            MemoryEvent.AddPair -> {
                increaseMatches()
                resetGame()
            }

            is MemoryEvent.CardClick -> {
                onCardClick(event.cardId)
            }

            MemoryEvent.ChangeTheme -> {
                setNextTheme()
            }

            MemoryEvent.ReducePairs -> {
                decreaseMatches()
                resetGame()
            }

            MemoryEvent.ResetGame -> {
                resetGame()
            }
        }
    }  // end of onClick function

    private fun setNextTheme() {
        _state.value = _state.value.copy(
            currentTheme = _state.value.currentTheme.getNextTheme())
    }
    private fun increaseMatches() {
        if (_state.value.pairCount < Constants.max_pairs) {
            _state.value = _state.value.copy(
                pairCount = _state.value.pairCount + 1
            )
        }
    }

    private fun decreaseMatches() {
        if (_state.value.pairCount > Constants.min_pairs) {
            _state.value = _state.value.copy(
                pairCount = _state.value.pairCount - 1
            )
        }
    }

    private fun compareValues(first: Int, second: Int) {
        val cards = _state.value.cards.copyOf() // make an array of cards
        if (second != null && first != null) {
            val card1 = cards[first]
            val card2 = cards[second]

            if (card1.value != card2.value) {   // no matches
                cards[first].flipCard()
                cards[second].flipCard()
            } else {
                cards[first].matchFound = true
                cards[second].matchFound = true
                _state.value = _state.value.copy(
                    cards = cards,
                    pairsMatched = _state.value.pairsMatched + 1
                )
            }
        }
        resetCompareCards()
    }

    private fun resetCompareCards() {
        if (_state.value.card2 != null) {
            _state.value = _state.value.copy(card1 = null, card2 = null)
        }
    }

    private fun resetGame() {
        _state.value = MemoryState(
            cards = generatCardsArray(_state.value.pairCount),
            pairCount = _state.value.pairCount,
            currentTheme = _state.value.currentTheme
        )
    }

    private fun cancelPreviousJob() {
        val firstIndex = _state.value.card1
        val secondIndex = _state.value.card2

        if (delayedCompareJob != null) {
            delayedCompareJob?.cancel()
            compareValues(firstIndex, secondIndex)
        }
    }

    private fun increaseClickCount(){
        _state.value = _state.value.copy(
            clickCount = _state.value.clickCount + 1
        )
    }


    private fun onCardClick(id: Int) {
        increaseClickCount()
        cancelPreviousJob()
        val cards = _state.value.cards  // get the array of cards
        if (cards[id].isBackDisplayed) {
            delayedCompareJob = viewModelScope.launch(Dispatchers.IO) {
                flip(id)
                val firstIndex = _state.value.card1
                val secondIndex = _state.value.card2
                val bothCardsAreNotNull = firstIndex != null && secondIndex != null
                val cardsMatchSkipDelay = if (bothCardsAreNotNull)
                    cards[firstIndex!!].value == cards[secondIndex!!].value
                else
                    false
                if(!cardsMatchSkipDelay)
                    delay(2000)
                compareValues(firstIndex, secondIndex)
            }
        }
    }

    private fun flip(id: Int) {
        val cards = _state.value.cards.copyOf()
        cards[id].flipCard()
        val card2 = _state.value.card1
        _state.value = _state.value.copy(
            card1 = id,
            card2 = card2,
            cards = cards
        )
    }
} // end of class