package com.techsnap.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout

class NewsDetailsActivity : AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var progressBarLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val url = intent.getStringExtra("url")

        webView = findViewById(R.id.webView)
        progressBarLayout = findViewById(R.id.progressBarLayout)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBarLayout.visibility = View.GONE
            }
        }

        webView.loadUrl(url)

        webView.settings.javaScriptEnabled = true
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}
