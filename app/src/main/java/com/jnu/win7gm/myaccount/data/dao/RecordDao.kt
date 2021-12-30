package com.jnu.win7gm.myaccount.data.dao
//
//import androidx.room.*
//import com.jnu.win7gm.myaccount.data.entity.Record
//
//@Dao
//interface RecordDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertRecords(vararg records: Record)
//
//    @Query("SELECT * FROM record")
//    fun getAllRecord(): List<Record>
//
//    @Query("SELECT * FROM  record WHERE date BETWEEN :minDate AND :maxDate")
//    fun getAllRecordsBetween(minDate: String, maxDate: String): List<Record>
//
//    @Query("SELECT * FROM  record WHERE money BETWEEN :minMoney AND :maxMoney")
//    fun getAllRecordsBetween(minMoney: Int, maxMoney: Int): List<Record>
//
//    @Query("SELECT * FROM record WHERE comment LIKE :search")
//    fun getRecordWithComment(search: String): List<Record>
//
//    @Query("SELECT * FROM record WHERE type = :type")
//    fun getAllByType(type: Char): List<Record>
//
//    // for common showcase, without account filter
//    @Query("SELECT date FROM record ORDER BY date DESC")
//    fun getDateList(): List<String>
//
//    @Query("SELECT * FROM record WHERE date = :date ORDER BY time DESC")
//    fun getByDate(date: String): List<Record>
//
//    @Query("SELECT SUM(money) FROM record WHERE date = :date AND type = :type")
//    fun sumByDateAndType(date: String, type: Char): Int
//
//    @Query("SELECT SUM(money) FROM record WHERE type = :type")
//    fun sumByType(type: Char): Int
//
//    // for account showcase, with account filter
//    @Query("SELECT date FROM record WHERE account = :account ORDER BY date DESC")
//    fun getDateListByAccount(account: String): List<String>
//
//    @Query("SELECT * FROM record WHERE date = :date AND account = :account ORDER BY time DESC")
//    fun getByDateByAccount(date: String, account: String): List<Record>
//
//    @Query("SELECT SUM(money) FROM record WHERE date = :date AND account = :account AND type = :type")
//    fun sumByDateAndTypeByAccount(date: String, type: Char, account: String): Int
//
//    @Update
//    fun updateRecords(vararg records: Record)
//
//    @Delete
//    fun deleteRecords(vararg records: Record)
//}