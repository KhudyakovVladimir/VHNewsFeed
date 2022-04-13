package com.khudyakovvladimir.vhnewsfeed.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NewsDAO {

    @Query("SELECT * FROM news")
    fun getNewsFromDatabase(): List<NewsEntity>
}