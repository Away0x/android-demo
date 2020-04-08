package com.away0x.room.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Word::class], version = 5, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    companion object {
        @Volatile private var instance: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WordDatabase {
            return Room.databaseBuilder(context.applicationContext, WordDatabase::class.java, "word_database")
                .addMigrations(MIGRATION_4_5)
                .build()
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1")
            }
        }

        private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL ,english_word TEXT," + "chinese_meaning TEXT)");
                database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning) " + "SELECT id,english_word,chinese_meaning FROM word");
                database.execSQL("DROP TABLE word");
                database.execSQL("ALTER TABLE word_temp RENAME to word");
            }
        }

        private val MIGRATION_4_5: Migration = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE word ADD COLUMN  chinese_invisible INTEGER NOT NULL DEFAULT 0");
            }
        }
    }

}