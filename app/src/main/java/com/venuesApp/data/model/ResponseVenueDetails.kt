package com.venuesApp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseVenueDetails(
    @SerializedName("venue")
    val venueWithDetails: VenueWithDetails
)