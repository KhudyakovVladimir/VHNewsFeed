package com.khudyakovvladimir.vhnewsfeed.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import java.net.URL

class NewsFeedAdapter(
    var context: Context,
    var list: List<NewsEntity>,
    var stubColor: Int
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    inner class FeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        lateinit var imageView: ImageView
        lateinit var textView: TextView
        lateinit var textViewTwo: TextView

        fun bind(newsEntity: NewsEntity) {
            imageView = itemView.findViewById(R.id.imageViewItem)
            textView = itemView.findViewById(R.id.textViewItem)
            textViewTwo = itemView.findViewById(R.id.textViewItem2)

            Log.d("TAG", "bind() - stubColor = $stubColor")

            val options: RequestOptions = RequestOptions()
                .placeholder(stubColor)
                .error(stubColor)

            Glide
                .with(context)
                .load(URL(newsEntity.urlToImage))
                .transform(RoundedCorners(50))
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(options)
                .into(imageView)

            textView.text = newsEntity.title
            textViewTwo.text = newsEntity.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderR = holder as FeedViewHolder
        holderR.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateAdapter(_listNews: List<NewsEntity>) {
        this.list.apply {
            list = emptyList()
            val tempList = list.toMutableList()
            val tempList2 = _listNews.toMutableList()
            tempList.addAll(tempList2)
            val resultList = tempList2.toList()
            list = resultList
        }
    }

}