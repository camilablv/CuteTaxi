package ua.com.cuteteam.passengerclient.application

import android.app.Application
import ua.com.cuteteam.passengerclient.di.ApplicationComponent
import ua.com.cuteteam.core.di.CoreModule
import ua.com.cuteteam.passengerclient.di.DaggerApplicationComponent

class AppClass: Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
        private set
    }

    private val coreModule: CoreModule by lazy {
        CoreModule(
            this
        )
    }



    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder().coreModule(coreModule).build()
    }
}
















