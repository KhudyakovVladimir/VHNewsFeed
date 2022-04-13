package com.khudyakovvladimir.vhnewsfeed.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import java.net.URI

class NewsFeedAdapter(
    var context: Context,
    var list: List<NewsEntity>
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

            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(context).load(URI(newsEntity.urlToImage)).apply(options).into(imageView)
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
}