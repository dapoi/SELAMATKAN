package com.dafdev.selamatkan.view.fragment.core.news

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.FragmentTopNewsBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.utils.InternetReceiver
import com.dafdev.selamatkan.view.adapter.news.NewsAdapter
import com.dafdev.selamatkan.viewmodel.NewsViewModel
import com.dafdev.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopNewsFragment : Fragment() {

    private lateinit var binding: FragmentTopNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var handler: Handler

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized) {
            binding
        } else {
            binding = FragmentTopNewsBinding.inflate(inflater, container, false)
            setViewModel()
            setAdapter()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolbar.setNavigationOnClickListener { activity?.finish() }

            imgSearchNews.setOnClickListener {
                findNavController().navigate(R.id.action_top_news_fragment_to_search_news_fragment)
            }

            HelpUtil.setStatusBarColor(requireActivity(), R.color.white, root)
        }

        handler = Handler(Looper.getMainLooper())
        swipeData()
    }

    private fun swipeData() {
        binding.apply {
            with(srlNews) {
                setLottieAnimation("loading.json")
                setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                    override fun onRefresh() {
                        val check = isOnline(requireActivity())
                        if (check) {
                            handler.postDelayed({
                                setRefreshing(false)
                            }, 2000)
                            handler.postDelayed({
                                InternetReceiver().onReceive(requireActivity(), Intent())
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
                    activity,
                    "Halaman yang Anda tuju tidak tersedia",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                HelpUtil.moveToChrome(requireActivity(), it.url)
            }
        }
    }

    private fun setViewModel() {
        newsViewModel.getNews.observe(viewLifecycleOwner) {
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
}