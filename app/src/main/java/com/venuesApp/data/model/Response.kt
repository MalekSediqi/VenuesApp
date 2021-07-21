package com.venuesApp.data.model


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("venues")
    val venues: List<Venue>
)