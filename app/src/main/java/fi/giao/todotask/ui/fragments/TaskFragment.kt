package fi.giao.todotask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import fi.giao.todotask.R
import fi.giao.todotask.ToDoActivity
import fi.giao.todotask.adapters.TasksAdapter
import fi.giao.todotask.databinding.FragmentTaskBinding
import fi.giao.todotask.model.TaskViewModel

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var taskAdapter: TasksAdapter
    private lateinit var viewModel: TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= (activity as ToDoActivity).viewModel
        binding = FragmentTaskBinding.inflate(inflater,container,false)
        binding.addTaskBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_taskFragment_to_addFragment)
        }
        setUpRecyclerView()
        viewModel.getAllTasks.observe(viewLifecycleOwner, Observer { taskLists ->
            taskAdapter.differ.submitList(taskLists)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskToDelete = taskAdapter.differ.currentList[position]
                viewModel.deleteTask(taskToDelete)
                Snackbar.make(binding.root,R.string.deleteSnackBar,Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.dismissSnackBar) {
                        viewModel.upsertTask(taskToDelete)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding.taskRecyclerView)
        return binding.root
    }

    private fun setUpRecyclerView() {
        taskAdapter = TasksAdapter()
        binding.taskRecyclerView.apply {
            adapter = taskAdapter
//            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context,2)
        }
    }
}