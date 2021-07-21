package com.venuesApp.data.model

import com.google.gson.annotations.SerializedName

data class VenueDetails(
    @SerializedName("response")
    val response: ResponseVenueDetails
)