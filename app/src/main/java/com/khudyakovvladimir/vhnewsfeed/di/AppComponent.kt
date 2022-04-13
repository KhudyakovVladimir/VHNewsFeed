package com.khudyakovvladimir.vhnewsfeed.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.khudyakovvladimir.vhnewsfeed.database.DBHelper
import com.khudyakovvladimir.vhnewsfeed.database.NewsDAO
import com.khudyakovvladimir.vhnewsfeed.database.NewsDatabase
import com.khudyakovvladimir.vhnewsfeed.news.NewsHelper
import com.khudyakovvladimir.vhnewsfeed.view.FeedFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [MainModule::class])
interface AppComponent {

    fun injectFeedFragment(feedFragment: FeedFragment)
    fun injectNewsHelper(newsHelper: NewsHelper)

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
    fun provideNewsHelper(newsDAO: NewsDAO): NewsHelper {
        return NewsHelper(newsDAO)
    }

    @Provides
    fun provideDBHelper(context: Context): DBHelper {
        return DBHelper(context)
    }

    @Provides
    fun provideNewsDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(application, NewsDatabase::class.java, "news_db").build()
    }

    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDAO()
}