package com.jnu.win7gm.myaccount.data.legacy

import android.content.Context
import com.jnu.win7gm.myaccount.data.BasicSL
import com.jnu.win7gm.myaccount.data.entity.Category
import com.jnu.win7gm.myaccount.data.entity.Record
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class DataBank(private val context: Context) : BasicSL {
    private lateinit var ctgList: MutableList<Category>
    private lateinit var recList: MutableList<Record>

    companion object {
        const val CTG_FILE_NAME = "categories"
        const val REC_FILE_NAME = "records"
    }

    override fun loadData() {
        loadCategory()
        loadRecord()
    }

    override fun saveData() {
        saveCategory()
        saveRecord()
    }

    fun loadCategory(): MutableList<Category> {
        ctgList = mutableListOf()
        try {
            val objectInputStream = ObjectInputStream(context.openFileInput(CTG_FILE_NAME))
            ctgList = objectInputStream.readObject() as MutableList<Category>
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ctgList
    }

    fun loadRecord(): MutableList<Record> {
        recList = mutableListOf()
        try {
            val objectInputStream = ObjectInputStream(context.openFileInput(REC_FILE_NAME))
            recList = objectInputStream.readObject() as MutableList<Record>
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return recList
    }

    fun saveCategory() {
        var objectOutputStream: ObjectOutputStream? = null
        try {
            objectOutputStream =
                ObjectOutputStream(context.openFileOutput(CTG_FILE_NAME, Context.MODE_PRIVATE))
            objectOutputStream.writeObject(ctgList)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                objectOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun saveRecord() {
        var objectOutputStream: ObjectOutputStream? = null
        try {
            objectOutputStream =
                ObjectOutputStream(context.openFileOutput(REC_FILE_NAME, Context.MODE_PRIVATE))
            objectOutputStream.writeObject(recList)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                objectOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}