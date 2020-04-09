package com.away0x.wordslog

import android.content.Context
import com.away0x.wordslog.data.room.WordDatabase
import com.away0x.wordslog.repositories.WordRepository
import com.away0x.wordslog.viewmodels.WordViewModelFactory

object Injection {

    private fun getWorkRepository(context: Context): WordRepository {
        val database = WordDatabase.getInstance(context)
        return WordRepository.getInstance(database.getWordDao())
    }

    fun provideWordViewModelFactory(context: Context): WordViewModelFactory {
        return WordViewModelFactory(getWorkRepository(context))
    }

}