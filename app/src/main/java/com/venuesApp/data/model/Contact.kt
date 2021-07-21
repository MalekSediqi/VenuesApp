package com.venuesApp.data.model


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("facebook")
    val facebook: String? = "",
    @SerializedName("facebookName")
    val facebookName: String? = "",
    @SerializedName("facebookUsername")
    val facebookUsername: String? = "",
    @SerializedName("formattedPhone")
    val formattedPhone: String? = "",
    @SerializedName("instagram")
    val instagram: String? = "",
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("twitter")
    val twitter: String? = ""
)