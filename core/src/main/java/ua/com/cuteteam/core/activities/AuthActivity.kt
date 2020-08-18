package ua.com.cuteteam.core.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.fragments.PhoneNumberFragment
import ua.com.cuteteam.core.fragments.VerificationCodeFragment
import ua.com.cuteteam.core.viewmodels.AuthViewModel
import ua.com.cuteteam.core.viewmodels.viewmodelfactories.AuthViewModelFactory
import javax.inject.Inject


class AuthActivity : AppCompatActivity() {

    companion object {
        private const val VERIFICATION_CODE_FRAGMENT = "VERIFICATION_CODE_FRAGMENT"
        private const val PHONE_NUMBER_FRAGMENT = "PHONE_NUMBER_FRAGMENT"
    }

    @Inject
    lateinit var authViewModel: AuthViewModel

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
                AuthViewModel.State.LOGGED_IN -> setResult()
                AuthViewModel.State.RESEND_CODE -> authViewModel.resendVerificationCode()
                AuthViewModel.State.SERVICE_UNAVAILABLE -> Toast.makeText(
                    this,
                    "To many requests",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                }
            }
        })
    }

    private fun setResult() {
        setResult(101)
        finish()
    }

    override fun onBackPressed() {
        val verificationCodeFragment =
            supportFragmentManager.findFragmentByTag(VERIFICATION_CODE_FRAGMENT)
        if (verificationCodeFragment?.isVisible == true) authViewModel.backToEnteringPhoneNumber()
        else finishAffinity()
    }
}
