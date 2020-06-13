package ua.com.cuteteam.core.directions

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ua.com.cuteteam.core.directions.interceptors.DirectionInterceptor

class DirectionServiceBuilder {

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/maps/api/directions/"
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(DirectionInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun build(): DirectionService = retrofit.create(DirectionService::class.java)
}
