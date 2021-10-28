package com.dafdev.selamatkan.view.fragment.main.bottom

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.FragmentArticleBinding
import com.dafdev.selamatkan.view.adapter.NewsAdapter
import com.dafdev.selamatkan.viewmodel.NewsViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            swipe.setColorSchemeColors(Color.BLUE)

            setViewModel()
            setAdapter()
            swipe.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    setViewModel()
                    setAdapter()
                    binding.swipe.isRefreshing = false
                }, 2000)
            }
        }
    }

    private fun setAdapter() {
        newsAdapter = NewsAdapter(requireActivity())
        binding.rvNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
        newsViewModel.getNews().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> progressBar(true)
                is Resource.Success -> {
                    progressBar(false)
                    newsAdapter.setNews(it.data!!)
                }
                is Resource.Error -> {
                    progressBar(false)
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun progressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}