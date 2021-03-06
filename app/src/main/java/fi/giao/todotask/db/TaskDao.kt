package fi.giao.todotask.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(task: Task):Long

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE taskName LIKE :query")
    fun searchTasks(query:String): LiveData<List<Task>>

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()

    @Query("SELECT * FROM tasks ORDER BY priority ASC")
    fun sortPriorityTask():LiveData<List<Task>>
}
