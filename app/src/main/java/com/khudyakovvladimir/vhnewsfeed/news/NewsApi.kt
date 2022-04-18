package com.khudyakovvladimir.vhnewsfeed.news

import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {

    @GET("top-headlines?country=ru&apiKey=e7284b9d8b2046848cd78f161bc69580")
    fun getNews(): Call<News>

    //https://newsapi.org/v2/top-headlines?country=ru&apiKey=e7284b9d8b2046848cd78f161bc69580

    // v2/everything

    // v2/top-headlines

    // v2/top-headlines/sources
}