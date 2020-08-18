package ua.com.cuteteam.core.di

import android.content.Context
import android.location.LocationProvider
import dagger.Module
import dagger.Provides
import ua.com.cuteteam.core.permissions.AccessFineLocationPermission
import javax.inject.Singleton

@Module
class MapsModule {

    @Singleton
    @Provides
    fun provideAccessFineLocationPermission(context: Context): AccessFineLocationPermission {
        return AccessFineLocationPermission(context)
    }
}