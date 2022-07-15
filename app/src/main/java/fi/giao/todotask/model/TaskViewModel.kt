package fi.giao.todotask.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fi.giao.todotask.db.Task
import fi.giao.todotask.db.TaskDatabase
import fi.giao.todotask.repository.Repository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application,private val repository: Repository): AndroidViewModel(application) {
    val getAllTasks : LiveData<List<Task>> = repository.getAllTasks()

    fun upsertTask(task: Task) = viewModelScope.launch { repository.upsertTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { repository.deleteTask(task) }
    fun searchTask(query:String)  =  repository.searchTasks(query)
    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }
    fun sortPriorityTask() =  repository.sortPriorityTask()
}