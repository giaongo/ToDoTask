package fi.giao.todotask.ui.fragments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fi.giao.todotask.model.TaskViewModel
import fi.giao.todotask.repository.Repository

class TaskViewModelProviderFactory(val app: Application,val repository: Repository) :  ViewModelProvider.Factory {
     override fun <T: ViewModel> create(modelClass: Class<T>): T {
         return TaskViewModel(app,repository) as T
     }
}