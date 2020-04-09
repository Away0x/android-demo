package com.away0x.wordslog.viewmodels

import androidx.lifecycle.*
import com.away0x.wordslog.data.room.Word
import com.away0x.wordslog.repositories.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepository: WordRepository) : ViewModel() {

    fun getAllWords() = wordRepository.getAllWords()

    fun findWordsWithPattern(pattern: String) = wordRepository.findWordsWithPattern(pattern)

    fun insertWords(vararg words: Word) {
        viewModelScope.launch {
            wordRepository.insertWords(*words)
        }
    }

    fun updateWords(vararg words: Word) {
        viewModelScope.launch {
            wordRepository.updateWords(*words)
        }
    }

    fun deleteWords(vararg words: Word) {
        viewModelScope.launch {
            wordRepository.deleteWords(*words)
        }
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            wordRepository.deleteAllWords()
        }
    }

}