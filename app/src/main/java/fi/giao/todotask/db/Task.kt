package fi.giao.todotask.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskName:String,
    val priority: Int,
    val timeStamp: Long)  :  Serializable