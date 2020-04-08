package com.away0x.wordslog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.away0x.wordslog.room.WordRepository

class WordViewModelFactory(private val wordRepository: WordRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(wordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}