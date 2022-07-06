package fi.giao.todotask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import fi.giao.todotask.R
import fi.giao.todotask.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater,container,false)
        binding.addTaskBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_taskFragment_to_addFragment)
        }
        return binding.root
    }
}