package com.dafdev.selamatkan.view.activity.main

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.data.domain.model.News
import com.dafdev.selamatkan.databinding.ActivityDetailNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            resultIntent?.url?.let { webView.loadUrl(it) }
            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url!!)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        const val EXTRA_DATA = "DATA"
    }
}