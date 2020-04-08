package com.away0x.room.repositories

import com.away0x.room.data.room.Word
import com.away0x.room.data.room.WordDao

class WordRepository private constructor(
    private val wordDao: WordDao
) {

    companion object {
        @Volatile private var instance: WordRepository? = null

        fun getInstance(wordDao: WordDao): WordRepository {
            return instance ?: synchronized(this) {
                instance ?: WordRepository(wordDao).also { instance = it }
            }
        }
    }

    fun getAllWordsLive() = wordDao.getAllWordsLive()

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