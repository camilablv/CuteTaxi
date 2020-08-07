package ua.com.cuteteam.passengerclient.di

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.BindsInstance

@Component
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
