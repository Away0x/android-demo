package com.away0x.room

import android.content.Context
import androidx.lifecycle.LiveData

class WordRepository(context: Context) {

    private val wordDao: WordDao
    private var allWordsLive: LiveData<List<Word>>

    init {
        val wordDatabase = WordDatabase.getInstance(context.applicationContext)
        wordDao = wordDatabase.getWordDao()
        allWordsLive = wordDao.getAllWordsLive()
    }

    fun getAllWordsLive(): LiveData<List<Word>> {
        return allWordsLive
    }

    suspend fun insertWords(vararg words: Word) {
        wordDao.insertWords(*words)
    }

    suspend fun updateWords(vararg words: Word) {
        wordDao.updateWords(*words)
    }

    suspend fun deleteWords(vararg words: Word) {
        wordDao.deleteWords(*words)
    }

    suspend fun deleteAllWords() {
        wordDao.deleteAllWords()
    }
}