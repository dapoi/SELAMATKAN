package com.dafdev.selamatkan.view.activity.core

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.ActivityNewsBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.utils.InternetReceiver
import com.dafdev.selamatkan.view.adapter.NewsAdapter
import com.dafdev.selamatkan.viewmodel.NewsViewModel
import com.dafdev.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private var _binding: ActivityNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var handler: Handler

    private val newsViewModel: NewsViewModel by viewModels()

//    private var packageManager = "com.android.chrome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        HelpUtil.setStatusBarColor(this, R.color.white, binding.root)

        handler = Handler(Looper.getMainLooper())

        swipeData()
        setViewModel()
        setAdapter()
    }

    private fun swipeData() {
        binding.apply {
            with(srlNews) {
                setLottieAnimation("loading.json")
                setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                    override fun onRefresh() {
                        val check = isOnline(this@NewsActivity)
                        if (check) {
                            handler.postDelayed({
                                setRefreshing(false)
                            }, 2000)
                            handler.postDelayed({
                                val status = InternetReceiver()
                                status.onReceive(this@NewsActivity, intent)
                            }, 2150)
                        } else {
                            HelpUtil.noInternetView(true, viewNoConnected, rvNews)
                            setRefreshing(false)
                        }
                    }
                })
            }
        }
    }

    private fun setAdapter() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
            setHasFixedSize(true)
        }
        newsAdapter.onItemClick = {
            if (it.url.isEmpty()) {
                Toast.makeText(
                    this,
                    "Halaman yang Anda tuju tidak tersedia",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                moveToChrome(it.url)
            }
        }
    }

    private fun setViewModel() {
        newsViewModel.getNews.observe(this) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        newsAdapter.setNews(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        viewNoConnected.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun moveToChrome(news: String?) {
        val builder = CustomTabsIntent.Builder()
        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(ContextCompat.getColor(this, R.color.white))
        builder.apply {
            setDefaultColorSchemeParams(params.build())
            setShowTitle(true)
            setShareState(CustomTabsIntent.SHARE_STATE_ON)
            setInstantAppsEnabled(true)
        }.build().also {
            it.launchUrl(this, Uri.parse(news))
        }

        /**
         * uncomment this if you want to open in chrome custom tab
         */
//        if (this.isPackageInstalled(packageManager)) {
//            customBuilder.intent.setPackage(packageManager)
//            customBuilder.launchUrl(this, Uri.parse(news))
//        } else {
//            Toast.makeText(
//                this,
//                "Maaf, halaman yang Anda tuju tidak tersedia",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }

//    private fun Context.isPackageInstalled(packageName: String): Boolean {
//        return try {
//            packageManager!!.getPackageInfo(packageName, 0)
//            true
//        } catch (e: PackageManager.NameNotFoundException) {
//            false
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}