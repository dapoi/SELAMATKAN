package com.dafdev.selamatkan.view.fragment.main.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.network.ApiConfig
import com.dafdev.selamatkan.databinding.FragmentArticleBinding
import com.dafdev.selamatkan.view.adapter.NewsAdapter
import com.dafdev.selamatkan.viewmodel.NewsViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status
import com.google.android.material.transition.MaterialFadeThrough

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiNews()))
        newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
        newsViewModel.getNews().observe(viewLifecycleOwner, {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> progressBar(true)
                    Status.SUCCESS -> {
                        progressBar(false)
                        newsAdapter.setNews(resource.data!!)
                    }
                    Status.ERROR -> {
                        progressBar(false)
                        Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                    }
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