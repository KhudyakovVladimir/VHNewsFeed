package com.khudyakovvladimir.vhnewsfeed.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDAO {

    @Query("SELECT * FROM news")
    fun getNewsFromDatabase(): List<NewsEntity>

    @Query("SELECT * FROM news")
    fun getAllNewsAsLiveData(): LiveData<List<NewsEntity>>?

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNoteById(id: Int): NewsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsEntity(newsEntity: NewsEntity)

    @Delete
    fun deleteNewsEntity(newsEntity: NewsEntity)

    @Update
    fun updateNewsEntity(newsEntity: NewsEntity)
}