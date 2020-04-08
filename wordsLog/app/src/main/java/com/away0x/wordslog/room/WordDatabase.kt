package com.away0x.wordslog.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Word::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WordDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    companion object {

        @Volatile private var instance: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WordDatabase {
            return Room.databaseBuilder(context.applicationContext, WordDatabase::class.java, "word_database")
                .build()
        }

    }

}