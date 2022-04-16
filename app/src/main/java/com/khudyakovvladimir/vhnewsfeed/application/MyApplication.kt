package com.khudyakovvladimir.vhnewsfeed.application

import android.app.Application
import android.content.Context
import com.khudyakovvladimir.vhnewsfeed.di.AppComponent
import com.khudyakovvladimir.vhnewsfeed.di.DaggerAppComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyApplication: Application() {

    lateinit var retrofit: Retrofit
    private set

    lateinit var appComponent: AppComponent
    private set

    override fun onCreate() {
        super.onCreate()

        TimeUnit.SECONDS.sleep(2)

        retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}

val Context.retrofit: Retrofit
get()= when(this) {
    is MyApplication -> retrofit
    else -> applicationContext.retrofit
}

val Context.appComponent: AppComponent
get() = when(this) {
    is MyApplication -> appComponent
    else -> applicationContext.appComponent
}