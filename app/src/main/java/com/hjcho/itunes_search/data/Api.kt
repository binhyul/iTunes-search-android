package com.hjcho.itunes_search.data

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiFactory @Inject constructor(
) {

    private val TIMEOUT_SEC = 100L

    private val logInterceptor: Interceptor
        get() = HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (message.length >= 2000) {
                    Platform.get().log("${message.subSequence(0, 2000)} SKIP message is too long")
                } else {
                    Platform.get().log(message)
                }
            }
        })
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }


    /**
     * OkhttpClient for building http request url
     */
    private fun getApiClient(): OkHttpClient {
        val builder = OkHttpClient()
            .newBuilder()
            .connectTimeout(
                TIMEOUT_SEC,
                TimeUnit.SECONDS
            )
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)

        return builder.build()
    }

    private val gson = GsonBuilder()
        .serializeNulls()
        .create()

    private fun getRetrofit(): Retrofit {
        val url = "https://itunes.apple.com/"
        return Retrofit.Builder()
            .client(getApiClient())
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val songApi: SongService by lazy {
        getRetrofit().create(SongService::class.java)
    }

}

