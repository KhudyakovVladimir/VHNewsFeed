package com.khudyakovvladimir.vhnewsfeed.news

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import com.khudyakovvladimir.vhnewsfeed.application.retrofit
import com.khudyakovvladimir.vhnewsfeed.database.NewsDAO
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import com.khudyakovvladimir.vhnewsfeed.recyclerview.NewsFeedAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL
import java.util.regex.Pattern
import javax.inject.Inject


class NewsHelper @Inject constructor(var newsDAO: NewsDAO) {

    fun getNewsAndSave(context: Context) {
        //Log.d("TAG", "getNewsAndSave()")

        context.retrofit.create(NewsApi::class.java).getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful) {
                    //Log.d("TAG", "getNewsAndSave() - onResponse() - isSuccessful")
                    CoroutineScope(Dispatchers.Main).launch {

                        newsDAO.deleteAllNews()

                        for (i in response.body()?.articles!!.indices) {

                            var title = response.body()?.articles!![i].title
                            var description = response.body()?.articles!![i].description
                            var urlToImage = response.body()?.articles!![i].urlToImage
                            var url = response.body()?.articles!![i].url

                            if(title == null)  title = ""
                            if(description == null) description = ""
                            if(urlToImage == null) urlToImage = URL("https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&text=photo&rpt=simage").toString()
                            val urlToImageToString = urlToImage.toString()
                            val urlToString = url.toString()

                            newsDAO.insertNewsEntity(NewsEntity(i, title, description, urlToImageToString, urlToString))
                        }
                    }
                }
                else {
                    //Log.d("TAG", "getNewsAndSave() - onResponse() - RESPONSE is NOT successful")
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                //Log.d("TAG", "getNewsAndSave() - onFailure")
            }
        })
    }

    fun getNewsAndReturnList(context: Context, newsFeedAdapter: NewsFeedAdapter): List<NewsEntity> {
        //Log.d("TAG", "getNewsAndReturnList()")

        val listNewsEntity = arrayListOf<NewsEntity>()
        listNewsEntity.clear()

        context.retrofit.create(NewsApi::class.java).getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful) {
                    Log.d("TAG", "getNewsAndReturnList - onResponse - isSuccessful")

                    CoroutineScope(Dispatchers.IO).launch {
                        val countOfNews = newsDAO.getCount()
                        //Log.d("TAG", "count of news = $countOfNews")

                        CoroutineScope(Dispatchers.Main).launch {

                            if(countOfNews != 0) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    newsDAO.deleteAllNews()
                                }
                            }

                            for (i in response.body()?.articles!!.indices) {

                                var title = response.body()?.articles!![i].title
                                var description = response.body()?.articles!![i].description
                                var urlToImage = response.body()?.articles!![i].urlToImage
                                var url = response.body()?.articles!![i].url

                                //Log.d("TAG", "i = $i")

                                if(urlToImage != null) {
                                    if(!checkURL(urlToImage)) {
                                        //Log.d("TAG", "WRONG_URL = $urlToImage")
                                        urlToImage = "https://${urlToImage}"
                                    }else {
                                        //Log.d("TAG", "Correct_URL = $urlToImage")
                                    }
                                }

                                if(url != null) {
                                    if(!checkURL(url)) {
                                        //Log.d("TAG", "WRONG_URL = $urlToImage")
                                        url = "https://${urlToImage}"
                                    }else {
                                        //Log.d("TAG", "Correct_URL = $urlToImage")
                                    }
                                }

                                if(title == null)  title = ""
                                if(description == null) description = ""
                                if(urlToImage == null) urlToImage = URL("https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&text=photo&rpt=simage").toString()
                                val urlToImageToString = urlToImage.toString()
                                val urlToString = url.toString()

                                CoroutineScope(Dispatchers.IO).launch {
                                    newsDAO.insertNewsEntity(NewsEntity(i, title, description, urlToImageToString, urlToString))
                                }
                                listNewsEntity.add(NewsEntity(i, title, description, urlToImageToString, urlToString))
                            }
                            newsFeedAdapter.list = listNewsEntity
                            newsFeedAdapter.notifyDataSetChanged()
                        }
                    }
                }
                else {
                    Log.d("TAG", "getNewsAndReturnList() - onResponse() - RESPONSE is NOT successful")
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                //Log.d("TAG", "getNewsAndReturnList() - onFailure")
                if(t is IOException) {
                    Log.d("TAG", "getNewsAndReturnList() - onFailure - is IOException")
                    Log.d("TAG", "getNewsAndReturnList() - onFailure - ${t.stackTraceToString()}")
                }else {
                    Log.d("TAG", "getNewsAndReturnList() - onFailure - conversion issue")
                }
            }
        })

        return listNewsEntity
    }

    fun checkURL(input: CharSequence): Boolean {
        if (TextUtils.isEmpty(input)) {
            return false
        }
        val URL_PATTERN: Pattern = Patterns.WEB_URL
        var isURL: Boolean = URL_PATTERN.matcher(input).matches()
        if (!isURL) {
            val urlString = input.toString() + ""
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    URL(urlString)
                    isURL = true
                } catch (e: Exception) {
                }
            }
        }
        return isURL
    }

}