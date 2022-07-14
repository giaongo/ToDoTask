package fi.giao.todotask.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
        taskAdapter.setOnItemClickListener { task ->
                val bundle = Bundle().apply {
                    putSerializable("task",task)
                }
                findNavController().navigate(R.id.action_taskFragment_to_updateFragment, bundle)
        }
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
                Snackbar.make(binding.root,R.string.delete_snackBar,Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.dismiss_snackBar) {
                        viewModel.upsertTask(taskToDelete)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding.taskRecyclerView)
        setHasOptionsMenu(true)
        hideKeyboard(requireActivity())
        return binding.root
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

    }
    private fun setUpRecyclerView() {
        taskAdapter = TasksAdapter()
        binding.taskRecyclerView.apply {
            adapter = taskAdapter
//            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context,2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu,menu)
        val actionMenuItem =  menu.findItem(R.id.taskSearch)
        val searchView = actionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null) {
                    runQuery(query)
                }
                return true
            }
        })
    }

    fun runQuery(query:String) {
        val searchQuery = "%$query%"
        viewModel.searchTask(searchQuery).observe(viewLifecycleOwner) { tasks ->
            if (tasks.isEmpty()) {
                Toast.makeText(requireActivity(), "Query not found", Toast.LENGTH_SHORT).show()
            }
            taskAdapter.differ.submitList(tasks)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)  {
            R.id.taskSearch -> {
                Toast.makeText(requireActivity(),"Search is clicked",Toast.LENGTH_SHORT).show()
                return true
            }
            else ->  {
                return super.onOptionsItemSelected(item)
            }

        }
    }
}