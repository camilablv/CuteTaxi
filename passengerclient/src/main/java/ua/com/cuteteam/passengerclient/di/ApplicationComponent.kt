package ua.com.cuteteam.passengerclient.di


import dagger.Component
import ua.com.cuteteam.core.activities.AuthActivity
import ua.com.cuteteam.core.activities.AuthModule
import ua.com.cuteteam.core.di.CoreModule
import ua.com.cuteteam.core.di.ViewModelModule

@Component(modules = [AuthModule::class , CoreModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: AuthActivity)
}
