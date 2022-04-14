package com.khudyakovvladimir.vhnewsfeed.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.khudyakovvladimir.vhnewsfeed.database.NewsEntity

class NewsDiffUtilCallback(
    private val oldList: List<NewsEntity>,
    private val newList: List<NewsEntity>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].description == newList[newItemPosition].description &&
                oldList[oldItemPosition].urlToImage == newList[newItemPosition].urlToImage
    }

}