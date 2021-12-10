package com.jnu.win7gm.myaccount.data.entity

import androidx.room.ColumnInfo
import java.sql.Date

data class DateClass(
    @ColumnInfo(name = "date") val date: Date?
)
