package com.venuesApp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Venue(
    @PrimaryKey
    @SerializedName(value="id")
    val id:String,
    @SerializedName(value="name")
    val title:String,
    @Embedded
    @SerializedName(value="location")
    val location:Location
)
