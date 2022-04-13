package com.khudyakovvladimir.vhnewsfeed.news

import android.R
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khudyakovvladimir.vhnewsfeed.application.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class NewsHelper @Inject constructor() {

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

    fun getNewsAndSave(context: Context, textViewTitle: TextView, textViewText: TextView, imageView: ImageView) {
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
}