package com.venuesApp.data.model


import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("count")
    val count: Int,

    @Ignore
    @SerializedName("groups")
    val groups: List<Group>
)