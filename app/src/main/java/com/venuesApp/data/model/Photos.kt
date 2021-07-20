package com.venuesApp.data.model


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("count")
    val count: Int,
    @Embedded
    @SerializedName("groups")
    val groups: List<Group>
)