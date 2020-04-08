package com.away0x.wordslog

import androidx.lifecycle.*
import com.away0x.wordslog.room.Word
import com.away0x.wordslog.room.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepository: WordRepository) : ViewModel() {

    private var allWords: LiveData<List<Word>> = MutableLiveData()

    fun getAllWords() {
        allWords = wordRepository.getAllWords()
    }

    fun findWordsWithPattern() {}

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