package com.techsnap.newsreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: NewsRecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager

    var newsList = arrayListOf<NewsItem>()
    val API_KEY = "55b0eb18098143628c2a1db983f9d39c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()

        recyclerView = findViewById(R.id.newsRecycler)
        layoutManager = LinearLayoutManager(this)

    }


    // Get data from api using Volley
    fun fetchData() {
        val queue = Volley.newRequestQueue(this@MainActivity)
        val url = "http://newsapi.org/v2/top-headlines?country=in&apiKey=" + API_KEY

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            Log.e("news", it.toString())
            Log.e("news", "entered request")
            val articles = it.getJSONArray("articles")

            for (i in 0 until articles.length()) {
                val article = articles.getJSONObject(i)

                // Convert to object
                val newsItem = NewsItem(
                    article.getJSONObject("source").getString("name"),
                    article.getString("title"),
                    article.getString("url"),
                    article.getString("urlToImage"),
                    article.getString("publishedAt")
                )
                // Add to list
                newsList.add(newsItem)
            }
            // Pass data to adapter
            recyclerAdapter = NewsRecyclerAdapter(this, newsList)
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = layoutManager

        }, Response.ErrorListener {
            Toast.makeText(this@MainActivity, "Volley Error", Toast.LENGTH_SHORT).show()
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
}
