package com.away0x.room.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.away0x.room.data.room.Word
import com.away0x.room.repositories.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    fun getAllWordsLive(): LiveData<List<Word>> {
        return wordRepository.getAllWordsLive()
    }

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