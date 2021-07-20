package com.venuesApp.data.model


import androidx.room.Embedded
import androidx.room.Entity
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
    @Embedded
    @SerializedName("contact")
    val contact: Contact,
    @Embedded
    @SerializedName("location")
    val location: Location,
    @Embedded
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("rating")
    val rating: Double
)