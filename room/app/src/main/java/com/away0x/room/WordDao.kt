package com.away0x.room

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

    @Query("SELECT * FROM WORD ORDER BY ID")
    fun getAllWordsLive(): LiveData<List<Word>>
}