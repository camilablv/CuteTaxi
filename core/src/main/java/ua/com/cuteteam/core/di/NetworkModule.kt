package ua.com.cuteteam.core.di

import dagger.Module
import dagger.Provides
import ua.com.cuteteam.core.directions.DirectionService
import ua.com.cuteteam.core.directions.DirectionServiceBuilder

@Module
class NetworkModule {

    @Provides
    fun provideDirectionService(): DirectionService {
        return DirectionServiceBuilder().build()
    }
}