package fi.giao.todotask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import fi.giao.todotask.db.TaskDatabase
import fi.giao.todotask.model.TaskViewModel
import fi.giao.todotask.repository.Repository
import fi.giao.todotask.ui.fragments.TaskViewModelProviderFactory

class ToDoActivity : AppCompatActivity() {
    lateinit var viewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = Repository(TaskDatabase.getDatabase(this).taskDao())
        val viewModelProviderFactory = TaskViewModelProviderFactory(application,repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(TaskViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this,navHostFragment.navController)
        navHostFragment.navController.navigate(R.id.taskFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() ||  super.onSupportNavigateUp()
    }

}