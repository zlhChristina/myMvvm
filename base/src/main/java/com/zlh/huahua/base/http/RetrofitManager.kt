package com.zlh.huahua.base.http

import com.zlh.huahua.base.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private var retrofit: Retrofit? = null
    private val DEFAULT_TIME_OUT = 5L                //超时时间 5s
    private val DEFAULT_READ_TIME_OUT = 10L

    private val BASE_URL = "https://api.woyeshi.cn/"

    private fun initRetrofit(): Retrofit {
        if (retrofit == null) {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)

            val interceptor = HttpLoggingInterceptor()
            if(BuildConfig.DEBUG){
                //显示日志
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            builder.addInterceptor(interceptor)

            retrofit = Retrofit.Builder().client(builder.build()).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun <T> create(service: Class<T>): T {
        if (retrofit == null) {
            initRetrofit()
        }
        return retrofit!!.create(service)
    }
}