package com.away0x.wordslog.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    @Insert
    suspend fun insertWords(vararg words: Word)

    @Update
    suspend fun updateWords(vararg words: Word)

    @Delete
    suspend fun deleteWords(vararg words: Word)

    @Query("DELETE FROM WORD")
    suspend fun deleteAllWords()

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM WORD WHERE english_word LIKE :pattern ORDER BY ID DESC")
    fun findWordsWithPattern(pattern: String): LiveData<List<Word>>

}