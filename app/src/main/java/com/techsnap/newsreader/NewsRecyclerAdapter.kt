package com.techsnap.newsreader

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class NewsRecyclerAdapter(val context: Context, val newsList: ArrayList<NewsItem>): RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    // ViewHolder
    class NewsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtSource: TextView = view.findViewById(R.id.txtSource)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val imgNewsImage: ImageView = view.findViewById(R.id.imgNewsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_news_row, parent, false)

        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.imgNewsImage.clipToOutline = true
        }

        val newsItem = newsList[position]
        holder.txtTitle.text = newsItem.title
        holder.txtSource.text = newsItem.source
        holder.txtDate.text = newsItem.date.subSequence(0, 10)
        Picasso.get().load(newsItem.imageUrl).error(R.mipmap.ic_launcher).into(holder.imgNewsImage)

        holder.txtTitle.setOnClickListener {
            val intent = Intent(context, NewsDetailsActivity::class.java)
            intent.putExtra("url", newsItem.newsUrl)
            context.startActivity(intent)
        }
    }

}