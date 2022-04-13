package com.khudyakovvladimir.vhnewsfeed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], exportSchema = false, version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDAO(): NewsDAO

    companion object {
        private const val NEWS_DATABASE = "news_db"
        var instance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase? {
            if(instance == null) {
                synchronized(this) {
                    instance =
                        Room
                            .databaseBuilder(context, NewsDatabase::class.java, NEWS_DATABASE)
                            .build()

                }
            }
            return instance
        }
    }


    fun destroyInstance() {
        instance = null
    }
}