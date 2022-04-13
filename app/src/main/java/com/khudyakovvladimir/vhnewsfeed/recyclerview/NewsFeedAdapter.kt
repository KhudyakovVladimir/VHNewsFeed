package com.khudyakovvladimir.vhnewsfeed.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.news.NewsHelper

class NewsFeedAdapter(
    var context: Context,
    var list: List<String>
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    inner class FeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        lateinit var imageView: ImageView
        lateinit var textView: TextView
        lateinit var textViewTwo: TextView

        fun bind(string: String) {
            imageView = itemView.findViewById(R.id.imageViewItem)
            textView = itemView.findViewById(R.id.textViewItem)
            textViewTwo = itemView.findViewById(R.id.textViewItem2)

            textView.text = string
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