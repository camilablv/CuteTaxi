package ua.com.cuteteam.core.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.fragments.HeadPieceFragment
import ua.com.cuteteam.core.viewmodels.AuthViewModel
import ua.com.cuteteam.core.viewmodels.StartUpViewModel
import ua.com.cuteteam.core.viewmodels.viewmodelfactories.AuthViewModelFactory
import ua.com.cuteteam.core.viewmodels.viewmodelfactories.StartUpViewModelFactory
import java.util.*

class StartActivity : AppCompatActivity() {

    private val authViewModel by lazy {
        ViewModelProvider(this, AuthViewModelFactory())
            .get(AuthViewModel::class.java)
    }

    private val startUpViewModel by lazy {
        ViewModelProvider(this, StartUpViewModelFactory())
            .get(StartUpViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.head_piece_fl, HeadPieceFragment())
            .commit()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                GlobalScope.launch(Dispatchers.Main) {
                    if (authViewModel.verifyCurrentUser()) {
                        authViewModel.firebaseUser?.let { updateUserAndStartActivity(it) }
                    }
                    else startAuthorization()
                }
            }
        }, 350)
    }

    private fun startAuthorization() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }

    private suspend fun updateUserAndStartActivity(firebaseUser: FirebaseUser) {
        startUpViewModel.updateOrCreateUser(firebaseUser)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.head_piece_fl, SupportMapFragment())
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
