package com.venuesApp.data.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class VenueWithDetails(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @Embedded(prefix = "contact_")
    @SerializedName("contact")
    val contact: Contact,
    @Embedded(prefix = "location_")
    @SerializedName("location")
    val location: Location,
    @SerializedName("rating")
    val rating: Double
)