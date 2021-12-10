package com.jnu.win7gm.myaccount.data.dao

import androidx.room.*
import com.jnu.win7gm.myaccount.data.entity.DateClass
import com.jnu.win7gm.myaccount.data.entity.Record
import java.sql.Date

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecords(vararg records: Record)

    @Query("SELECT * FROM record")
    fun getAllRecord(): List<Record>

    @Query("SELECT * FROM  record WHERE date BETWEEN :minDate AND :maxDate")
    fun getAllRecordsBetween(minDate: Date, maxDate: Date): List<Record>

    @Query("SELECT * FROM  record WHERE money BETWEEN :minMoney AND :maxMoney")
    fun getAllRecordsBetween(minMoney: Int, maxMoney: Int): List<Record>

    @Query("SELECT * FROM record WHERE comment LIKE :search")
    fun findRecordWithComment(search: String): List<Record>

    @Query("SELECT * FROM record WHERE id = :id")
    fun getAllByType(id: Int): List<Record>

    @Query("SELECT date FROM record")
    fun getAvailableDate(): List<DateClass>

    @Query("SELECT * FROM record WHERE date = :date")
    fun getByDate(date: Date): List<Record>

    @Query("SELECT SUM(money) FROM record WHERE date = :date AND type = :type")
    fun sumByDateAndType(date: Date, type: Char): Int

    @Update
    fun updateRecords(vararg records: Record)

    @Delete
    fun deleteRecords(vararg records: Record)
}