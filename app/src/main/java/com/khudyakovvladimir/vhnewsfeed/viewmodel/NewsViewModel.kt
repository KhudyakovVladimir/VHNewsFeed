package com.khudyakovvladimir.vhnewsfeed.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.khudyakovvladimir.vhnewsfeed.database.NewsDAO
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import com.khudyakovvladimir.vhnewsfeed.news.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    application: Application,
    val newsDAO: NewsDAO
) : AndroidViewModel(application) {

    private var listNews: LiveData<List<NewsEntity>>? = newsDAO.getAllNewsAsLiveData()

    fun getListNews(): LiveData<List<NewsEntity>>? {
        Log.d("TAG", "NewsViewModel - getListNews()")
        viewModelScope.launch {
            //listNews = newsDAO.getAllNewsAsLiveData()!!
        }
        return listNews
    }

//    fun getNewsFromDB(): List<NewsEntity> {
//        var list = listOf<NewsEntity>()
//        CoroutineScope(Dispatchers.IO).launch {
//            val job = launch {
//                list = newsDAO.getNewsFromDatabase()
//                //listNews.value = list
//            }
//            job.join()
//        }
//        return list
//    }

    fun getNewsFromDB(): LiveData<List<NewsEntity>>? {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                listNews = newsDAO.getAllNewsAsLiveData()
            }
            job.join()
        }
        return listNews
    }

    fun getNewsById(id: Int): NewsEntity? {
        var resultNews: NewsEntity? = null
        runBlocking {
            val job = launch {
                resultNews = newsDAO.getNewsById(id)
            }
            job.join()
        }
        return resultNews
    }
}