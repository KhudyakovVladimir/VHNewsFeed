package com.khudyakovvladimir.vhnewsfeed.database

import androidx.room.*

@Dao
interface NewsDAO {

    @Query("SELECT * FROM news")
    fun getNewsFromDatabase(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsEntity(newsEntity: NewsEntity)

    @Delete
    fun deleteNewsEntity(newsEntity: NewsEntity)

    @Update
    fun updateNewsEntity(newsEntity: NewsEntity)
}