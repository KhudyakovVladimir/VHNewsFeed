package com.khudyakovvladimir.vhnewsfeed.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.application.appComponent
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import com.khudyakovvladimir.vhnewsfeed.news.NewsHelper
import com.khudyakovvladimir.vhnewsfeed.utils.AnimationHelper
import com.khudyakovvladimir.vhnewsfeed.utils.OnHorizontalSwipeListener
import com.khudyakovvladimir.vhnewsfeed.utils.SystemHelper
import com.khudyakovvladimir.vhnewsfeed.viewmodel.NewsViewModel
import com.khudyakovvladimir.vhnewsfeed.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

class SingleNews: Fragment() {

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

    lateinit var imageViewSingleNews: ImageView
    lateinit var textViewSingleNewsTitle: TextView
    lateinit var textViewSingleNewsDescription: TextView
    lateinit var singleNewsLinearLayout: LinearLayout


    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectSingleNews(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.single_news_layout, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewSingleNews = view.findViewById(R.id.imageViewSingleNews)
        textViewSingleNewsTitle = view.findViewById(R.id.textViewSingleNewsTitle)
        textViewSingleNewsDescription = view.findViewById(R.id.textViewSingleNewsDescription)
        singleNewsLinearLayout = view.findViewById(R.id.singleNewsLinearLayout)

        newsViewModelFactory = factory.createNewsViewModelFactory(activity!!.application)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)

        var countOfNews = 0

        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                countOfNews = newsViewModel.newsDAO.getCount()
            }
            job.join()
            Log.d("TAG", "SingleNews - countOfNews = $countOfNews")
        }

        var id = arguments?.getInt("newsId",0)
        Log.d("TAG", "newsId = $id")

        setContent(id!!)

        singleNewsLinearLayout.setOnTouchListener(object: OnHorizontalSwipeListener(context!!) {
            override fun onRightSwipe() {

                if(id > 0 && id < countOfNews - 1) {
                    setContent(id--!!)
                    Log.d("TAG", "SingleNews - onRightSwipe() - id = $id")
                }

            }

            override fun onLeftSwipe() {

                if(id > 0 && id < countOfNews - 1) {
                    setContent(id++!!)
                    Log.d("TAG", "SingleNews - onLeftSwipe() - id = $id")
                }

            }
        })

    }

    private fun setContent (id: Int) {
        Log.d("TAG", "SingleNews - setContent()")
        var tempNews: NewsEntity
        CoroutineScope(Dispatchers.IO).launch {
            var tempNews = newsViewModel.getNewsById(id!!)!!
            //Log.d("TAG", "${tempNews.title}")

            CoroutineScope(Dispatchers.Main).launch {
                if (tempNews.urlToImage != null) {
                    val options: RequestOptions = RequestOptions()
                        .placeholder(stubColor)
                        .error(stubColor)

                    Glide
                        .with(context!!)
                        .load(URL(tempNews.urlToImage))
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .apply(options)
                        .into(imageViewSingleNews)
                }else {
                    imageViewSingleNews.setImageResource(R.drawable.stub_black)
                }

                textViewSingleNewsTitle.text = tempNews.title
                textViewSingleNewsDescription.text = tempNews.description
            }
        }
    }
}