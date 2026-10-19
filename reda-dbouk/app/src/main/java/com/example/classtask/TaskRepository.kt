package com.example.classtask

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao = TaskDao()) {
    fun getTasks(): Flow<List<Task>> = taskDao.getTasks()

    suspend fun update(task: Task) {
        taskDao.update(task)
    }
}
