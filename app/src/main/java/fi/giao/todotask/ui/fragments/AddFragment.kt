package fi.giao.todotask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fi.giao.todotask.R
import fi.giao.todotask.ToDoActivity
import fi.giao.todotask.databinding.FragmentAddBinding
import fi.giao.todotask.db.Task
import fi.giao.todotask.model.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment(){
    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel : TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =  (activity as ToDoActivity).viewModel
        binding = FragmentAddBinding.inflate(inflater,container,false)
        val myAdapter = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_dropdown_item,resources.getStringArray(R.array.task_priorities))
        binding.apply {
            prioritySpinner.adapter = myAdapter
            addButton.setOnClickListener {
                val taskText =  filledTaskText.editText!!.text
                val priority = prioritySpinner.selectedItemPosition
                val timeStamp = System.currentTimeMillis()
                viewModel.upsertTask(Task(0,taskText.toString(),priority,timeStamp))
                findNavController().navigate(R.id.action_addFragment_to_taskFragment)
            }
        }
        return binding.root
    }

}
