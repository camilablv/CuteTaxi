package ua.com.cuteteam.cutetaxi.fragments.settings

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.preference.EditTextPreference
import ua.com.cuteteam.cutetaxi.R
import ua.com.cuteteam.cutetaxi.data.database.DbEntries

class MainInfoFragment : BaseSettingsFragment() {

    override val resourceId: Int
        get() = R.xml.main_info_preferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.onCreatePreferences(savedInstanceState, rootKey)

        findPreference<EditTextPreference>(DbEntries.Passengers.Fields.PHONE)?.setOnBindEditTextListener {
            it.setSingleLine()
            it.inputType = EditorInfo.TYPE_CLASS_PHONE
        }

    }

}