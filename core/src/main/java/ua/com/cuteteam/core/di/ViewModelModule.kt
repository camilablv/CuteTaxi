package ua.com.cuteteam.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import ua.com.cuteteam.core.viewmodels.viewmodelfactories.MapViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: MapViewModelFactory): ViewModelProvider.Factory

}