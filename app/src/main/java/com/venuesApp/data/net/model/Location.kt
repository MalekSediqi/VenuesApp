package com.venuesApp.data.net.model

data class Location(
    val address: String,
    val cc: String,
    val city: String,
    val country: String,
    val distance: Int,
    val formattedAddress: List<String>,
    val labeledLatLngs: List<LabeledLatLng>,
    val lat: Double,
    val lng: Double,
    val neighborhood: String,
    val postalCode: String,
    val state: String
)