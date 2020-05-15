package ua.com.cuteteam.core.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.fragments.PhoneNumberFragment
import ua.com.cuteteam.core.fragments.VerificationCodeFragment
import ua.com.cuteteam.core.viewmodels.AuthViewModel
import ua.com.cuteteam.core.viewmodels.viewmodelfactories.AuthViewModelFactory


class AuthActivity : AppCompatActivity() {

    companion object {
        private const val VERIFICATION_CODE_FRAGMENT = "VERIFICATION_CODE_FRAGMENT"
        private const val PHONE_NUMBER_FRAGMENT = "PHONE_NUMBER_FRAGMENT"
    }

    private val authViewModel by lazy {
        ViewModelProvider(this, AuthViewModelFactory())
            .get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        authViewModel.state.observe(this, Observer {
            val transaction = supportFragmentManager.beginTransaction()
            when (it) {
                AuthViewModel.State.ENTERING_PHONE_NUMBER -> transaction.replace(
                    R.id.auth_fl,
                    PhoneNumberFragment(),
                    PHONE_NUMBER_FRAGMENT
                )
                    .addToBackStack(PHONE_NUMBER_FRAGMENT)
                    .commit()
                AuthViewModel.State.ENTERING_VERIFICATION_CODE -> transaction.replace(
                    R.id.auth_fl,
                    VerificationCodeFragment(),
                    VERIFICATION_CODE_FRAGMENT
                )
                    .addToBackStack(VERIFICATION_CODE_FRAGMENT)
                    .commit()
                AuthViewModel.State.LOGGED_IN -> returnToStartUpActivity()
                AuthViewModel.State.RESEND_CODE -> authViewModel.resendVerificationCode()
                else -> {
                }
            }
        })
    }

    private fun returnToStartUpActivity() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val verificationCodeFragment =
            supportFragmentManager.findFragmentByTag(VERIFICATION_CODE_FRAGMENT)
        if (verificationCodeFragment?.isVisible == true) authViewModel.backToEnteringPhoneNumber()
        else finishAffinity()
    }
}
