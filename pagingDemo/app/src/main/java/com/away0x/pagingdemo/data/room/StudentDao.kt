package com.away0x.pagingdemo.data.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudents(vararg students: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAllStudents()

    @Query("SELECT * FROM student_table ORDER BY id")
    fun getAllStudents(): DataSource.Factory<Int, Student>

}