package ua.com.cuteteam.cutetaxi.viewmodels.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.com.cuteteam.cutetaxi.repositories.DriverRepository
import ua.com.cuteteam.cutetaxi.viewmodels.DriverViewModel

@Suppress("UNCHECKED_CAST")
class DriverViewModelFactory(private val repo: DriverRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = DriverViewModel(repo) as T
}