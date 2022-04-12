package com.khudyakovvladimir.vhnewsfeed.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.news.NewsHelper
import com.khudyakovvladimir.vhnewsfeed.recyclerview.NewsFeedAdapter

class FeedFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsFeedAdapter: NewsFeedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val newsHelper = NewsHelper()
//        val v = newsHelper.getNews(activity!!.applicationContext)

        val list = listOf("one", "two", "three")

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        newsFeedAdapter = NewsFeedAdapter(activity!!.applicationContext, list)
        recyclerView.adapter = newsFeedAdapter


    }
}