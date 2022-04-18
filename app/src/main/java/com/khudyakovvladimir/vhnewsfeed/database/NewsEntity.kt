package com.khudyakovvladimir.vhnewsfeed.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    var title: String,

    @ColumnInfo(name = "description", typeAffinity = ColumnInfo.TEXT)
    var description: String,

    @ColumnInfo(name = "urlToImage", typeAffinity = ColumnInfo.TEXT)
    var urlToImage: String,

    @ColumnInfo(name = "url", typeAffinity = ColumnInfo.TEXT)
    var url: String
)