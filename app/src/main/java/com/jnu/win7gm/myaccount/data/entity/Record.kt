package com.jnu.win7gm.myaccount.data.entity

import java.io.Serializable
import java.util.*

//@Entity(tableName = "record")
data class Record(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
    val id: UUID,

//    @ColumnInfo(name="date")
    val date: String,

//    @ColumnInfo(name="time")
    val time: String,

//    @ColumnInfo(name = "account")
    val account: String,

//    @ColumnInfo(name = "category")
    val category: String,

//    @ColumnInfo(name = "type")
    val type: Char,

//    @ColumnInfo(name = "money")
    val money: Double,

//    @ColumnInfo(name = "comment", defaultValue = "")
    val comment: String?
) : Serializable