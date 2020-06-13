package ua.com.cuteteam.core.directions.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ua.com.cuteteam.core.BuildConfig

class DirectionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().url(
                chain.request().url().newBuilder()
                    .addQueryParameter("key", BuildConfig.GoogleAPIKey)
                    .build()
            ).build()
        )
    }
}