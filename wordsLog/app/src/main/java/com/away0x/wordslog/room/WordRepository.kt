package com.away0x.wordslog.room

import android.content.Context
import androidx.lifecycle.LiveData

class WordRepository private constructor(
    private val wordDao: WordDao
) {
    companion object {
        @Volatile
        private var instance: WordRepository? = null

        fun getInstance(wordDao: WordDao): WordRepository {
            return instance ?: synchronized(this) {
                instance ?: WordRepository(wordDao).also { instance = it }
            }
        }
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

    fun getAllWords(): LiveData<List<Word>> {
        return wordDao.getAllWords()
    }

    fun findWordsWithPattern(pattern: String): LiveData<List<Word>> {
        return wordDao.findWordsWithPattern("%${pattern}%")
    }

}