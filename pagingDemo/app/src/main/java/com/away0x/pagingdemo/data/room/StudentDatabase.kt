package com.away0x.pagingdemo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun getStudentDao(): StudentDao

    companion object {

        @Volatile private var instance: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): StudentDatabase {
            return Room.databaseBuilder(context.applicationContext, StudentDatabase::class.java, "students_database")
                .build()
        }

    }

}