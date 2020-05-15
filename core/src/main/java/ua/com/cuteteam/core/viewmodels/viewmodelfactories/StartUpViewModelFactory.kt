package ua.com.cuteteam.core.viewmodels.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.com.cuteteam.core.repositories.StartUpRepository
import ua.com.cuteteam.core.viewmodels.StartUpViewModel

@Suppress("UNCHECKED_CAST")
class StartUpViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartUpViewModel(StartUpRepository()) as T
    }
}