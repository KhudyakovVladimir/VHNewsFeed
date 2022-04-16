package com.khudyakovvladimir.vhnewsfeed.database

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DBHelper(private val context: Context) {

    lateinit var newsDatabase: NewsDatabase

    fun createDatabase() {
        newsDatabase = NewsDatabase.getInstance(context)!!

        val sharedPreferences = context.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        if (sharedPreferences.contains("database")) {
            Log.d("TAG", "isDatabaseCreated = true")
        }else {
            CoroutineScope(Dispatchers.IO).launch {
                newsDatabase.newsDAO().insertNewsEntity(NewsEntity(1, "pull down", "to update", "https://yandex.ru/images/search?text=photo&from=tabbar&pos=4&img_url=https%3A%2F%2Foboi.ws%2Foriginals%2Foriginal_5834_oboi_bolota_na_fone_gor_4500x3008.jpg&rpt=simage"))
            }
        }

    }
}