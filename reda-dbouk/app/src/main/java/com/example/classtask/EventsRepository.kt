package com.example.classtask

class EventsRepository(private val api: CampusApi = CampusApi.create()) {

    suspend fun getEvents(): List<CampusEvent> = api.getEvents().map { it.toCampusEvent() }
}

fun EventDto.toCampusEvent(): CampusEvent = CampusEvent(
    id = id,
    title = title,
    startsAt = startsAt.replace("T", " "),
    location = location ?: "Location TBA"
)
