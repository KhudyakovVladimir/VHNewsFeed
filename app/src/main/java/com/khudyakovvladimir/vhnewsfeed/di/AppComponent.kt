package com.khudyakovvladimir.vhnewsfeed.di

import android.app.Application
import com.khudyakovvladimir.vhnewsfeed.news.NewsHelper
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [MainModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application):Builder
    }
}

@Module
class MainModule {

    @Provides
    fun provideNewsHelper(): NewsHelper {
        return NewsHelper()
    }
}