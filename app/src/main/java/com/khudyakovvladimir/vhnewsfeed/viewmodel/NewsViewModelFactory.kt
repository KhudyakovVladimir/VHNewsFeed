package com.khudyakovvladimir.vhnewsfeed.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khudyakovvladimir.vhnewsfeed.database.NewsDAO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.lang.IllegalArgumentException

class NewsViewModelFactory @AssistedInject constructor(
    @Assisted("application")
    var application: Application,
    var newsDAO: NewsDAO
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED CAST")
            return NewsViewModel(
                application = application,
                newsDAO = newsDAO
            ) as T
        }
        throw IllegalArgumentException("Unable to construct NoteViewModel")
    }

    @AssistedFactory
    interface Factory {
        fun createNewsViewModelFactory(@Assisted("application") application: Application): NewsViewModelFactory
    }

}