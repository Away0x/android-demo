package com.away0x.room.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "english_word")
    val word: String,

    @ColumnInfo(name = "chinese_meaning")
    val chineseMeaning: String,

    @ColumnInfo(name = "chinese_invisible")
    var chineseInvisible: Boolean = false
)