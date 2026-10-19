package com.example.classtask

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskDao {
    private val _tasks = MutableStateFlow(
        listOf(
            Task(1L, "Buy groceries", false),
            Task(2L, "Walk the dog", true),
            Task(3L, "Finish MVVM refactoring", false)
        )
    )

    fun getTasks(): Flow<List<Task>> = _tasks.asStateFlow()

    suspend fun update(task: Task) {
        _tasks.update { list ->
            list.map { if (it.id == task.id) task else it }
        }
    }
}
