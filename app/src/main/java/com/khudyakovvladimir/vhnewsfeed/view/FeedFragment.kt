package com.khudyakovvladimir.vhnewsfeed.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.application.appComponent
import com.khudyakovvladimir.vhnewsfeed.database.DBHelper
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import com.khudyakovvladimir.vhnewsfeed.news.NewsHelper
import com.khudyakovvladimir.vhnewsfeed.recyclerview.NewsFeedAdapter
import com.khudyakovvladimir.vhnewsfeed.viewmodel.NewsViewModel
import com.khudyakovvladimir.vhnewsfeed.viewmodel.NewsViewModelFactory
import javax.inject.Inject

class FeedFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsFeedAdapter: NewsFeedAdapter
    private var isDatabaseCreated = false

    @Inject
    lateinit var newsHelper: NewsHelper

    @Inject
    lateinit var factory: NewsViewModelFactory.Factory
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsViewModelFactory: NewsViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectFeedFragment(this)

        val sharedPreferences = activity?.applicationContext!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        if (sharedPreferences.contains("database")) {
            Log.d("TAG", "isDatabaseCreated = true")
            isDatabaseCreated = sharedPreferences.getBoolean("database", false)
        }

        if (!isDatabaseCreated) {
            Log.d("TAG", "isDatabaseCreated = false")
            val dbHelper = activity?.let { DBHelper(it) }
            dbHelper?.createDatabase()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("database", true)
            editor.apply()
        }

        newsHelper.getNewsAndSave(activity!!.applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModelFactory = factory.createNewsViewModelFactory(activity!!.application)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)

        val list = listOf(NewsEntity(1,"title", "description", "https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&text=photo&rpt=simage"))

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        newsFeedAdapter = NewsFeedAdapter(activity!!.applicationContext, list)
        recyclerView.adapter = newsFeedAdapter

        newsViewModel.getListNews().observe(this) {
            newsFeedAdapter.list = it
        }

    }
}