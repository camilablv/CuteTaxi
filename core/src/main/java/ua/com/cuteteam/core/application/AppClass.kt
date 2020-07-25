package ua.com.cuteteam.core.application

import android.app.Application
import android.content.Context
import ua.com.cuteteam.core.R

class AppClass: Application() {

    init {
        instance = this
    }

    companion object {

        private var instance: AppClass? = null

        fun appContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        this.setTheme(R.style.Theme_MaterialComponents_DayNight_NoActionBar)
    }
}
