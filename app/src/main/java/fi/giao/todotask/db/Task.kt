package fi.giao.todotask.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskName:String,
    val priority: Int,
    val timeStamp: Long)