package com.techsnap.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class NewsDetailsActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val url = intent.getStringExtra("url")

        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
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
