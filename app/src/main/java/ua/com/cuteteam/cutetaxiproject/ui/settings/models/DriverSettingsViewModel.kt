package ua.com.cuteteam.cutetaxiproject.ui.settings.models

import ua.com.cuteteam.cutetaxiproject.ui.settings.SettingsViewModel

class DriverSettingsViewModel : SettingsViewModel(){

    private var vmCallback: CuteTaxiSettingsCallback? = null


    override fun setup(callback: CuteTaxiSettingsCallback) {
        vmCallback = callback

        TODO("Update settings for Driveer profile")

    }


}