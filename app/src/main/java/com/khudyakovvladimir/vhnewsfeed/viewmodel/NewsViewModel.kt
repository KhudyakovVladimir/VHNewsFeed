package com.khudyakovvladimir.vhnewsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.khudyakovvladimir.vhnewsfeed.database.NewsDAO
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    application: Application,
    val newsDAO: NewsDAO
) : AndroidViewModel(application) {

    private var listNews: LiveData<List<NewsEntity>> = newsDAO.getAllNewsAsLiveData()!!

    fun getListNews(): LiveData<List<NewsEntity>> {
        viewModelScope.launch {
            newsDAO.getAllNewsAsLiveData()
        }
        return listNews
    }
}