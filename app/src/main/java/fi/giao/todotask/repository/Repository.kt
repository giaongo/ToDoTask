package fi.giao.todotask.repository

import fi.giao.todotask.db.Task
import fi.giao.todotask.db.TaskDao

class Repository(private val taskDao: TaskDao) {
    suspend fun upsertTask(task: Task) = taskDao.upsertTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    fun getAllTasks() = taskDao.getAllTasks()
    fun searchTasks(query: String) = taskDao.searchTasks(query)
}