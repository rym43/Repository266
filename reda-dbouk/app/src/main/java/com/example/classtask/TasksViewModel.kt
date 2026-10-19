package com.example.classtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: TaskRepository) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = repository.getTasks()
        .map<List<Task>, TasksUiState> { tasks ->
            TasksUiState.Content(tasks)
        }
        .catch {
            emit(TasksUiState.Content(emptyList()))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TasksUiState.Loading
        )

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.update(task.copy(isDone = !task.isDone))
            } catch (e: Exception) {
                // Gracefully handle error without crashing
            }
        }
    }

    companion object {
        fun provideFactory(repository: TaskRepository = TaskRepository()): androidx.lifecycle.ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    TasksViewModel(repository)
                }
            }
    }
}
