package com.venuesApp.data.net.model

data class Response(
    val confident: Boolean,
    val venues: List<Venue>
)