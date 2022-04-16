package com.khudyakovvladimir.vhnewsfeed.news

import android.content.Context
import android.util.Log
import com.khudyakovvladimir.vhnewsfeed.application.retrofit
import com.khudyakovvladimir.vhnewsfeed.database.NewsDAO
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import javax.inject.Inject

class NewsHelper @Inject constructor(var newsDAO: NewsDAO) {

    fun getNewsAndSave(context: Context) {
        context.retrofit.create(NewsApi::class.java).getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        for (i in response.body()?.articles!!.indices) {

                            var title = response.body()?.articles!![i].title
                            var description = response.body()?.articles!![i].description
                            var urlToImage = response.body()?.articles!![i].urlToImage

                            if(title == null)  title = ""
                            if(description == null) description = ""
                            if(urlToImage == null) urlToImage = URL("https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&text=photo&rpt=simage")
                            val urlToString = urlToImage.toString()

                            newsDAO.insertNewsEntity(NewsEntity(i, title, description, urlToString))
                        }
                    }
                }
                else {
                    Log.d("TAG", "RESPONSE is NOT successful")
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
            }
        })
    }

    fun getNewsAndReturnList(context: Context): List<NewsEntity> {

        val listNewsEntity = arrayListOf<NewsEntity>()

        context.retrofit.create(NewsApi::class.java).getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        for (i in response.body()?.articles!!.indices) {

                            var title = response.body()?.articles!![i].title
                            var description = response.body()?.articles!![i].description
                            var urlToImage = response.body()?.articles!![i].urlToImage

                            if(title == null)  title = ""
                            if(description == null) description = ""
                            if(urlToImage == null) urlToImage = URL("https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&text=photo&rpt=simage")
                            val urlToString = urlToImage.toString()

                            newsDAO.insertNewsEntity(NewsEntity(i, title, description, urlToString))
                            listNewsEntity.add(NewsEntity(i, title, description, urlToString))
                        }
                    }
                }
                else {
                    Log.d("TAG", "RESPONSE is NOT successful")
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
            }
        })

        return listNewsEntity
    }
}