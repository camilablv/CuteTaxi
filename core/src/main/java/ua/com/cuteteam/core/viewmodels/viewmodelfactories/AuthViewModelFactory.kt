package ua.com.cuteteam.core.viewmodels.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import ua.com.cuteteam.core.providers.AuthProvider
import ua.com.cuteteam.core.viewmodels.AuthViewModel

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(
            AuthProvider(
                PhoneAuthProvider.getInstance(),
                FirebaseAuth.getInstance()
            )
        ) as T
    }
}