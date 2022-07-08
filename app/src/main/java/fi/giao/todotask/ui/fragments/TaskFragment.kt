package fi.giao.todotask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        return binding.root
    }

    private fun setUpRecyclerView() {
        taskAdapter = TasksAdapter()
        binding.taskRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}