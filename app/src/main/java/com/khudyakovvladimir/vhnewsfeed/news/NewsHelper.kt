package com.khudyakovvladimir.vhnewsfeed.news

import android.R
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    fun getNews(context: Context, textViewTitle: TextView, textViewText: TextView, imageView: ImageView) {
        context.retrofit.create(NewsApi::class.java).getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        //set value in all available views
                        Log.d("TAG", "${response.body()?.articles!![0].urlToImage}")
                        textViewTitle.text = response.body()?.articles!![0].title
                        textViewText.text = response.body()?.articles!![0].description

                        val options: RequestOptions = RequestOptions()
                            .placeholder(R.drawable.btn_star)
                            .error(R.drawable.btn_star)

                        Glide
                            .with(context)
                            .load(response.body()?.articles!![0].urlToImage)
                            .apply(options)
                            .into(imageView)
                    }
                }
                else {
                    Log.d("TAG", "RESPONSE is NOT successful")
                    //println(response.code())
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                //set values from cache
            }

        })
    }

    fun getNewsAndSave(context: Context) {
        context.retrofit.create(NewsApi::class.java).getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
//                        val title = response.body()?.articles!![0].title
//                        val description = response.body()?.articles!![0].description
//                        val urlToImage = response.body()?.articles!![0].urlToImage

                        Log.d("TAG", "size = ${response.body()?.articles!!.size}")

                        for (i in 1..response.body()?.articles!!.size - 2) {

                            //all fields need to null check

                            val title = response.body()?.articles!![i].title
                            val description = response.body()?.articles!![i].description
                            var urlToImage = response.body()?.articles!![i].urlToImage

                            if(urlToImage == null) {
                                urlToImage = URL("https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&text=photo&rpt=simage")
                            }

                            val urlToString = urlToImage.toString()

                            Log.d("TAG", "id = $i TITLE = $title")
                            Log.d("TAG", "urlToString = $urlToString")

                            CoroutineScope(Dispatchers.IO).launch {
                                //newsDAO.updateNewsEntity(NewsEntity(i, title, description, urlToString))
                            }
                        }

                    }
                }
                else {
                    Log.d("TAG", "RESPONSE is NOT successful")
                    //println(response.code())
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                //set values from cache
            }

        })
    }
}