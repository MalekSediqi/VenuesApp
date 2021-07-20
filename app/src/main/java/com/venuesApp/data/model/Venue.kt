package com.venuesApp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Venue(
    @PrimaryKey
    val id:String,
    val title:String,
    val location:String
)
