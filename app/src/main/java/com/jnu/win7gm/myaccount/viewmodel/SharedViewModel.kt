package com.jnu.win7gm.myaccount.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.jnu.win7gm.myaccount.R
import com.jnu.win7gm.myaccount.data.entity.Category
import com.jnu.win7gm.myaccount.data.entity.Record
import com.jnu.win7gm.myaccount.data.legacy.DataBank
import java.util.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val dataBank = DataBank(application)
    var categoryList: MutableList<Category> = dataBank.loadCategory()
    var recordList: MutableList<Record> = dataBank.loadRecord()

    init {
        val defaultCtg = mutableListOf<Category>()
        defaultCtg.add(Category("其他", R.drawable.other, '-'))
        defaultCtg.add(Category("饮食", R.drawable.food, '-'))
        defaultCtg.add(Category("交通", R.drawable.vehicle, '-'))
        defaultCtg.add(Category("娱乐", R.drawable.game, '-'))
        defaultCtg.add(Category("通讯", R.drawable.tel, '-'))
        defaultCtg.add(Category("送礼", R.drawable.gift, '-'))
        defaultCtg.add(Category("日用", R.drawable.daily, '-'))
        defaultCtg.add(Category("医疗", R.drawable.medical2, '-'))
        defaultCtg.add(Category("房租水电", R.drawable.home, '-'))
        defaultCtg.add(Category("工资", R.drawable.salary2, '+'))
        defaultCtg.add(Category("股票基金", R.drawable.fund, '+'))
        defaultCtg.add(Category("生活费", R.drawable.redpacket, '+'))
        defaultCtg.add(Category("外快", R.drawable.salary, '+'))
        defaultCtg.add(Category("旅游", R.drawable.travel, '-'))
        val defaultRec = mutableListOf<Record>()
        defaultRec.add(
            Record(
                UUID.randomUUID(),
                "2021-12-28",
                "00:41:39",
                "现金",
                "娱乐",
                '-',
                5.34,
                "抽奖"
            )
        )
        defaultRec.add(
            Record(
                UUID.randomUUID(),
                "2021-12-29",
                "00:42:54",
                "现金",
                "饮食",
                '-',
                15.68,
                "北门糖水"
            )
        )
        defaultRec.add(
            Record(
                UUID.randomUUID(),
                "2021-12-29",
                "00:42:32",
                "现金",
                "饮食",
                '-',
                3.00,
                "北门糖水"
            )
        )
        defaultRec.add(
            Record(
                UUID.randomUUID(),
                "2021-12-30",
                "03:42:23",
                "现金",
                "生活费",
                '+',
                2000.00,
                ""
            )
        )
        defaultRec.add(
            Record(
                UUID.randomUUID(),
                "2021-12-30",
                "06:13:39",
                "现金",
                "外快",
                '+',
                500.01,
                "外包好累"
            )
        )

        val sharedPref = application.getSharedPreferences(
            "com.jnu.win7gm.myaccount.PREFERENCE_FILE",
            Context.MODE_PRIVATE
        )
        val firstBoot = sharedPref.getBoolean("isFirstBoot", true)

        with(sharedPref.edit()) {
            putBoolean("isFirstBoot", false)
            apply()
        }

        if (firstBoot) {
            categoryList.addAll(defaultCtg)
            recordList.addAll(defaultRec)
            dataBank.saveData()
        }
    }

    fun getCategoryMap(): MutableMap<String, Category> {
        var tmp = mutableMapOf<String, Category>()
        categoryList.forEach {
            tmp[it.name] = it
        }
        return tmp
    }
}