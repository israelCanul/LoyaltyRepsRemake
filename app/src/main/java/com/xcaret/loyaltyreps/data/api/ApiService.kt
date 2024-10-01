package com.xcaret.loyaltyreps.data.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


abstract class APIService() {

    protected abstract val baseUrl: String
    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(90, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
//        .addInterceptor(ResponseInterceptor())
        .build()
    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    open fun getApi() = api
}

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val modified = response.newBuilder()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()

        return modified
    }
}