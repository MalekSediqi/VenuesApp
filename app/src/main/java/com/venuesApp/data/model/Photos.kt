package com.venuesApp.data.model


import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("count")
    val count: Int,

    @SerializedName("groups")
    val groups: List<Group>
)