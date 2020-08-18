package ua.com.cuteteam.core.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_phone_number.*
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.viewmodels.AuthViewModel
import javax.inject.Inject


class PhoneNumberFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phone_number_et.setText(authViewModel.phoneNumber)

        phone_number_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(phoneNumber: Editable?) {
                authViewModel.phoneNumber = phoneNumber.toString()
                continue_btn.isEnabled = phone_number_et.text?.isNotEmpty() ?: false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        continue_btn.setOnClickListener(this)
        continue_btn.isEnabled = phone_number_et.text?.isNotEmpty() ?: false

        authViewModel.state.observe(viewLifecycleOwner, Observer {
            when(it) {
                AuthViewModel.State.INVALID_PHONE_NUMBER -> phone_number_et.error = getString(R.string.invalid_phone_number_error)
                else -> {}
            }
        })
    }

    override fun onClick(view: View?) {
        when(view) {
            continue_btn -> {
                val phoneNumber = phone_number_et.text.toString()
                //AppSettingsHelper(this.context!!).phone = phoneNumber
                authViewModel.verifyPhoneNumber(phoneNumber)
            }
        }
    }
}
