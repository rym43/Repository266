package com.example.classtask

import com.google.gson.annotations.SerializedName

// Exact shape of one event in the JSON the server sends back.
data class EventDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("starts_at") val startsAt: String,
    @SerializedName("location") val location: String?
)
