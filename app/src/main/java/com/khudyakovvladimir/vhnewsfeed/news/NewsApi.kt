package com.khudyakovvladimir.vhnewsfeed.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines?country=ru&apiKey=e7284b9d8b2046848cd78f161bc69580")
    fun getNews(): Call<News>

    //q=bitcoin&
    @GET("everything?from=2022-04-18&to=2022-04-18&sortBy=popularity&apiKey=e7284b9d8b2046848cd78f161bc69580")
    fun getNewsByTopic(@Query("q") topic: String): Call<News>

    // https://newsapi.org/v2/top-headlines?country=ru&apiKey=e7284b9d8b2046848cd78f161bc69580

    // apiKey=e7284b9d8b2046848cd78f161bc69580

    // top-headlines?country=ru&apiKey=e7284b9d8b2046848cd78f161bc69580

    // everything?q=bitcoin&from=2022-04-18&to=2022-04-18&sortBy=popularity&apiKey=e7284b9d8b2046848cd78f161bc69580

    // everything

    // top-headlines

    // top-headlines/sources
}