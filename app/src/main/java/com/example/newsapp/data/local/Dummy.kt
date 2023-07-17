package com.example.newsapp.data.local

import com.example.newsapp.domain.model.responses.Article
import com.google.gson.Gson

object Dummy {
    val article: Article = Gson().fromJson(
        "{\n" +
                "            \"source\": {\n" +
                "                \"id\": \"techcrunch\",\n" +
                "                \"name\": \"TechCrunch\"\n" +
                "            },\n" +
                "            \"author\": \"Alex Wilhelm\",\n" +
                "            \"title\": \"Is this what an early-stage slowdown looks like?\",\n" +
                "            \"description\": \"Hello and welcome back to our regular morning look at private companies, public markets and the gray space in between. Today we’re exploring some fascinating data from Silicon Valley Bank markets report for Q1 2020. We’re digging into two charts that deal wit…\",\n" +
                "            \"url\": \"http://techcrunch.com/2020/02/10/is-this-what-an-early-stage-slowdown-looks-like/\",\n" +
                "            \"urlToImage\": \"https://techcrunch.com/wp-content/uploads/2020/02/GettyImages-dv1637047.jpg?w=556\",\n" +
                "            \"publishedAt\": \"2020-02-10T17:06:42Z\",\n" +
                "            \"content\": \"Hello and welcome back to our regular morning look at private companies, public markets and the gray space in between.\\r\\nToday we’re exploring some fascinating data from Silicon Valley Bank markets report for Q1 2020. We’re digging into two charts that deal wi… [+648 chars]\"\n" +
                "        }", Article::class.java
    )
}