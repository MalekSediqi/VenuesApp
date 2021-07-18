package com.venuesApp.data.net.model

data class Venue(
    val categories: List<Category>,
    val hasPerk: Boolean,
    val id: String,
    val location: Location,
    val name: String,
    val referralId: String
)