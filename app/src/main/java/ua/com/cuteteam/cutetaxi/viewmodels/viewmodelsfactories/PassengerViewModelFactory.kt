package ua.com.cuteteam.cutetaxi.viewmodels.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.com.cuteteam.cutetaxi.repositories.PassengerRepository
import ua.com.cuteteam.cutetaxi.viewmodels.PassengerViewModel

@Suppress("UNCHECKED_CAST")
class PassengerViewModelFactory(private val passengerRepository: PassengerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PassengerViewModel(passengerRepository) as T
    }
}
