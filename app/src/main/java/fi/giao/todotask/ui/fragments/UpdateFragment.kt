package fi.giao.todotask.ui.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fi.giao.todotask.R
import fi.giao.todotask.ToDoActivity
import fi.giao.todotask.databinding.FragmentUpdateBinding
import fi.giao.todotask.db.Task
import fi.giao.todotask.model.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewModel :  TaskViewModel
    val args: UpdateFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater,container,false)
        viewModel = (activity as ToDoActivity).viewModel
        val task = args.task
        binding.apply {
            edTxtUpdateTask.setText(task.taskName)
            priorityUpdateSpinner.setSelection(task.priority)
            updateButton.setOnClickListener {
                val taskText = edTxtUpdateTask.text.toString()
                val taskPriority = priorityUpdateSpinner.selectedItemPosition
                viewModel.upsertTask(Task(task.id,taskText,taskPriority,task.timeStamp))
                findNavController().navigate(R.id.action_updateFragment_to_taskFragment)
            }
        }
        return binding.root
    }
}