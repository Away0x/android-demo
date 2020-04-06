package com.away0x.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val wordRepository: WordRepository by lazy {
        WordRepository(application)
    }

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