package com.venuesApp.data.model


import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("crossStreet")
    val crossStreet: String,
    @SerializedName("postalCode")
    val postalCode: String,
    @SerializedName("state")
    val state: String
)