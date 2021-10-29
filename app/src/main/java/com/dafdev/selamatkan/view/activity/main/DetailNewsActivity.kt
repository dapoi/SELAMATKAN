package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.data.domain.model.News
import com.dafdev.selamatkan.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }

        val resultIntent = intent.getParcelableExtra<News>(EXTRA_DATA)
        binding.apply {
            webView.webViewClient = WebViewClient()
            resultIntent?.url?.let { webView.loadUrl(it) }
        }
    }

    companion object {
        const val EXTRA_DATA = "DATA"
    }
}