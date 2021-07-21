package com.venuesApp.data.model


import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("count")
    val count: Int,
    @Ignore
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)