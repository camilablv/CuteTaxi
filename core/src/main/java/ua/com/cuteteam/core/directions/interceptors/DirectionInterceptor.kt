package ua.com.cuteteam.core.directions.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.application.AppClass

class DirectionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().url(
                chain.request().url().newBuilder()
                    .addQueryParameter("key", AppClass.appContext().getString(R.string.google_server_key))
                    .build()
            ).build()
        )
    }
}