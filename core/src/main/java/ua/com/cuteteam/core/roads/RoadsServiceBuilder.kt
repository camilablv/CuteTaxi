package ua.com.cuteteam.core.roads

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ua.com.cuteteam.core.directions.interceptors.DirectionInterceptor

class RoadsServiceBuilder {

    companion object {
        private const val BASE_URL = "https://roads.googleapis.com/v1/"
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(DirectionInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(RoadsServiceBuilder.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun build(): RoadsService = retrofit.create(RoadsService::class.java)
}

