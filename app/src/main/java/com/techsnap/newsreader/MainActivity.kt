package com.techsnap.newsreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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

        val url = "http://newsapi.org/v2/top-headlines?country=in&apiKey=" + API_KEY
        fetchData(url)

        recyclerView = findViewById(R.id.newsRecycler)
        layoutManager = LinearLayoutManager(this)
        recyclerAdapter = NewsRecyclerAdapter(this, newsList)

    }

    // Get data from api using Volley
    fun fetchData(url: String) {
        val queue = Volley.newRequestQueue(this@MainActivity)

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            Log.e("news", it.toString())
            val articles = it.getJSONArray("articles")

            // Clear newsList first
            newsList.clear()

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
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = layoutManager
            recyclerAdapter.notifyDataSetChanged()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView?

        searchView?.queryHint = "Search for News"
        searchView?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val url = "http://newsapi.org/v2/everything?q=${query}&apiKey=$API_KEY"
                    fetchData(url)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            }
        )
        return super.onCreateOptionsMenu(menu)
    }
}
