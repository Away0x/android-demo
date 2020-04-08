package com.away0x.room

import android.content.Context
import com.away0x.room.data.room.WordDatabase
import com.away0x.room.repositories.WordRepository
import com.away0x.room.viewmodels.WordViewModelFactory

object Injection {

    private fun getWordRepository(context: Context): WordRepository {
        val database = WordDatabase.getInstance(context)
        return WordRepository.getInstance(database.getWordDao())
    }

    fun provideWordViewModelFactory(context: Context): WordViewModelFactory {
        val repository = getWordRepository(context)
        return WordViewModelFactory(repository)
    }

}