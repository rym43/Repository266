package com.example.classtask

sealed interface TasksUiState {
    object Loading : TasksUiState
    data class Content(val tasks: List<Task>) : TasksUiState
}
