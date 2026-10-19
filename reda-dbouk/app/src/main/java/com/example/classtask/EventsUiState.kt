package com.example.classtask

sealed interface EventsUiState {
    object Loading : EventsUiState
    data class Content(val events: List<CampusEvent>) : EventsUiState
    data class Error(val message: String) : EventsUiState
}
