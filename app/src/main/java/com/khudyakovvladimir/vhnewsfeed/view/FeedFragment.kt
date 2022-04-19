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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.application.appComponent
import com.khudyakovvladimir.vhnewsfeed.database.DBHelper
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import com.khudyakovvladimir.vhnewsfeed.news.NewsHelper
import com.khudyakovvladimir.vhnewsfeed.recyclerview.NewsFeedAdapter
import com.khudyakovvladimir.vhnewsfeed.utils.AnimationHelper
import com.khudyakovvladimir.vhnewsfeed.utils.SystemHelper
import com.khudyakovvladimir.vhnewsfeed.viewmodel.NewsViewModel
import com.khudyakovvladimir.vhnewsfeed.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsFeedAdapter: NewsFeedAdapter
    private var isDatabaseCreated = false
    private lateinit var fab: FloatingActionButton

    @Inject
    lateinit var newsHelper: NewsHelper

    @Inject
    lateinit var animationHelper: AnimationHelper

    @Inject
    lateinit var systemHelper: SystemHelper

    private var stubColor = R.drawable.stub_white

    @Inject
    lateinit var factory: NewsViewModelFactory.Factory
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsViewModelFactory: NewsViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectFeedFragment(this)

        stubColor = when(systemHelper.checkTheme(context)) {
            true -> R.drawable.stub_black
            false -> R.drawable.stub_white
        }

        val sharedPreferences = activity?.applicationContext!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        if (sharedPreferences.contains("database")) {
            isDatabaseCreated = sharedPreferences.getBoolean("database", false)
        }

        if (!isDatabaseCreated) {
            val dbHelper = activity?.let { DBHelper(it) }
            dbHelper?.createDatabase()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("database", true)
            editor.apply()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var topic = arguments?.getString("topic","")
        if(topic == null) {
            topic = "earth"
        }

        Log.d("TAG", "topic = $topic")

        newsViewModelFactory = factory.createNewsViewModelFactory(activity!!.application)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)

        val list = listOf(NewsEntity(0,"pull down", "to update", "https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&text=photo&rpt=simage", ""))

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.visibility = View.INVISIBLE
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

        val itemClick = { newsEntity: NewsEntity -> navigateToSingleNews(newsEntity.id)}

        newsFeedAdapter =
            NewsFeedAdapter(
                activity!!.applicationContext,
                list,
                stubColor,
                itemClick
            )
        recyclerView.adapter = newsFeedAdapter

        animationHelper.fadeInView(recyclerView, 1500)

        newsViewModel.getListNews()?.observe(this) {
            newsFeedAdapter.list = it

            if(systemHelper.isConnectionAvailable(context!!)) {
                Log.d("TAG", "data from Internet")
                //newsHelper.getNewsAndReturnList(activity!!.applicationContext, newsFeedAdapter)
                newsHelper.getNewsByTopic(activity!!.applicationContext, newsFeedAdapter, topic!!)
                newsFeedAdapter.notifyDataSetChanged()
            }else {
                Log.d("TAG", "data from DB")
                CoroutineScope(Dispatchers.Main).launch {
                    val job = launch {
                        newsViewModel.getNewsFromDB()
                        newsFeedAdapter.notifyDataSetChanged()
                    //Log.d("TAG", "list = ${newsFeedAdapter.list}")
                    }
                    job.join()


                }
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //newsHelper.getNewsAndReturnList(activity!!.applicationContext, newsFeedAdapter)
                    //newsHelper.getNewsByTopic(activity!!.applicationContext, newsFeedAdapter, topic!!)
                    //newsFeedAdapter.notifyDataSetChanged()
                    CoroutineScope(Dispatchers.IO).launch {
                        //Log.d("TAG", "count of news = ${newsViewModel.newsDAO.getCount()}")
                    }
                }
            }
        })

        fab = view.findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            findNavController().navigate(R.id.topicFragment)
        }
    }

    private fun navigateToSingleNews(newsId: Int) {
        Log.d("TAG", "navigateToSingleNews() - newsId =  $newsId")
        val bundle = Bundle()
        bundle.putInt("newsId", newsId)
        findNavController().navigate(R.id.singleNews, bundle)
    }

}