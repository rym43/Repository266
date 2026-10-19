package com.example.classtask

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CampusApi {
    @GET("events.json")
    suspend fun getEvents(): List<EventDto>

    companion object {
        // events.json lives in the reda-dbouk/ folder of the GitHub repo.
        const val BASE_URL =
            "https://raw.githubusercontent.com/redadbouk/Campus-Companion/reda-dbouk-events/reda-dbouk/"

        fun create(): CampusApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CampusApi::class.java)
    }
}
