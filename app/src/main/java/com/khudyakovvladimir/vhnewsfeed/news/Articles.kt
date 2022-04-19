package com.khudyakovvladimir.vhnewsfeed.news

import java.net.URL

data class Articles(
    var author: String,
    var title: String,
    var description: String,
    //var urlToImage: URL,
    var urlToImage: String,
    var url: String
)
