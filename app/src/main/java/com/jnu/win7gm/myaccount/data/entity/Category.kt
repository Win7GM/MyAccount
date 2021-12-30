package com.jnu.win7gm.myaccount.data.entity

import java.io.Serializable

data class Category(
    val name: String,
    val icon: Int,
    val defaultType: Char
) : Serializable
