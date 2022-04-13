package com.khudyakovvladimir.vhnewsfeed.recyclerview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.khudyakovvladimir.vhnewsfeed.R
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity
import java.net.URI
import java.net.URL

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
                .placeholder(R.drawable.feed_icon)
                .error(R.drawable.ic_launcher_background)

            Glide
                .with(context)
                .load(URL(newsEntity.urlToImage))
                //.transition(DrawableTransitionOptions.withCrossFade())
                //.placeholder(R.drawable.feed_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.feed_icon)
                .apply(options)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("TAG", "${newsEntity.title} - onLoadFailed")
                        imageView.setImageResource(R.drawable.feed_icon)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("TAG", "${newsEntity.title} - onResourceReady")
                        target!!.onResourceReady(resource!!, DrawableCrossFadeTransition(1000, isFirstResource))
                        return true
                    }

                })
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
}