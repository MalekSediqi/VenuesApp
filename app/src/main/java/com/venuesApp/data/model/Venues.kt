package com.venuesApp.data.model


import com.google.gson.annotations.SerializedName

data class Venues(
    @SerializedName("response")
    val response: Response
)