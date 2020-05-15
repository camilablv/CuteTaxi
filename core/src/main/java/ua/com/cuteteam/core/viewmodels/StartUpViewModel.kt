package ua.com.cuteteam.core.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ua.com.cuteteam.core.repositories.StartUpRepository

class StartUpViewModel(private val repository: StartUpRepository) : ViewModel() {

    suspend fun updateOrCreateUser(firebaseUser: FirebaseUser) =
        repository.updateOrCreateUser(firebaseUser)
}
