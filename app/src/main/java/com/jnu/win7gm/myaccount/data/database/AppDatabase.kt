package com.jnu.win7gm.myaccount.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jnu.win7gm.myaccount.data.converter.Converters
import com.jnu.win7gm.myaccount.data.dao.RecordDao
import com.jnu.win7gm.myaccount.data.entity.Record


@Database(entities = [Record::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "db"
        private var databaseInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (databaseInstance == null) {
                databaseInstance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).allowMainThreadQueries()
                    .build()
            }
            return databaseInstance
        }
    }

    abstract fun recordDao(): RecordDao
}
