package fi.giao.todotask.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import fi.giao.todotask.R
import fi.giao.todotask.databinding.TaskViewBinding
import fi.giao.todotask.db.Task
import kotlinx.android.synthetic.main.task_view.view.*
import java.text.DateFormat

class TasksAdapter: RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val binding: TaskViewBinding): RecyclerView.ViewHolder(binding.root)
    private val differCallBack = object : DiffUtil.ItemCallback<Task>()  {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
        return TaskViewHolder(TaskViewBinding.inflate(view,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        val task = differ.currentList[position]
        val priority = context.resources.getStringArray(R.array.task_priorities)[task.priority]
            holder.binding.apply {
            taskText.text = task.taskName
            taskPriority.text = priority
            taskTimeStamp.text = DateFormat.getInstance().format(task.timeStamp)
        }
        when(task.priority) {
            0 -> holder.binding.taskCard.setCardBackgroundColor(ContextCompat.getColor(context,R.color.dark_pink))
            1  -> holder.binding.taskCard.setCardBackgroundColor(ContextCompat.getColor(context,R.color.medium_pink))
            else -> holder.binding.taskCard.setCardBackgroundColor(ContextCompat.getColor(context,R.color.light_pink))
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}