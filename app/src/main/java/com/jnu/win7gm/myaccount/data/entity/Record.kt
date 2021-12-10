package com.jnu.win7gm.myaccount.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "record")
data class Record(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "date")
    val date: Date?,

    @ColumnInfo(name = "account")
    val account: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "type")
    val type: Char,

    @ColumnInfo(name = "money")
    val money: Int,

    @ColumnInfo(name = "comment", defaultValue = "")
    val comment: String?
)