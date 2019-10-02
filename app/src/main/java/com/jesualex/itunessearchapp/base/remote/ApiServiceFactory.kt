package com.jesualex.itunessearchapp.base.remote

import com.jesualex.itunessearchapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jesualex on 16-03-19.
 */
object ApiServiceFactory {
    @JvmOverloads fun <T> build(
        serviceClass: Class<T>,
        urlBase: String,
        client: OkHttpClient.Builder = OkHttpClient.Builder()
    ): T {
        if (BuildConfig.DEBUG) {
            activateDebug(client)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }


    private fun activateDebug(client: OkHttpClient.Builder) {
        val logging = HttpLoggingInterceptor()
        logging.level = Level.BODY
        client.interceptors().add(logging)
    }
}
