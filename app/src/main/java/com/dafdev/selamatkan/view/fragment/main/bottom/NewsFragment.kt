package com.dafdev.selamatkan.view.fragment.main.bottom

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentNewsBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.noInternetView
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.NewsAdapter
import com.dafdev.selamatkan.viewmodel.NewsViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter

    private val newsViewModel: NewsViewModel by viewModels()

    private var packageManager = "com.android.chrome"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HelpUtil.setStatusBarWhite(requireActivity(), R.color.white)

        swipeData()

        setViewModel()
        setAdapter()
    }

    private fun swipeData() {
        binding.apply {
            srlNews.setOnRefreshListener {
                val check = isOnline(requireActivity())
                if (check) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        setAdapter()
                        setViewModel()
                        noInternetView(false, viewNoConnected, rvNews)
                        srlNews.isRefreshing = false
                    }, 2000)
                } else {
                    noInternetView(true, viewNoConnected, rvNews)
                    srlNews.isRefreshing = false
                }
            }
        }
    }

    private fun setAdapter() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
        newsAdapter.onItemClick = {
            moveToChrome(it.url)
        }
    }

    private fun moveToChrome(news: String?) {
        val builder = CustomTabsIntent.Builder()
        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.white))
        builder.apply {
            setDefaultColorSchemeParams(params.build())
            setShowTitle(true)
            setShareState(CustomTabsIntent.SHARE_STATE_ON)
            setInstantAppsEnabled(true)
        }

        val customBuilder = builder.build()
        if (requireActivity().isPackageInstalled(packageManager)) {
            customBuilder.intent.setPackage(packageManager)
            customBuilder.launchUrl(requireContext(), Uri.parse(news))
        } else {
            Toast.makeText(
                requireContext(),
                "Maaf, halaman yang anda tuju tidak tersedia",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun Context.isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager!!.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun setViewModel() {
        newsViewModel.getNews.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        it.data?.let { data -> newsAdapter.setNews(data) }
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        viewNoConnected.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}