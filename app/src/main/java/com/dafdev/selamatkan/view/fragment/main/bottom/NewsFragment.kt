package com.dafdev.selamatkan.view.fragment.main.bottom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.FragmentNewsBinding
import com.dafdev.selamatkan.view.activity.main.DetailNewsActivity
import com.dafdev.selamatkan.view.adapter.NewsAdapter
import com.dafdev.selamatkan.viewmodel.NewsViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            setViewModel()
            setAdapter()
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
            Intent(activity, DetailNewsActivity::class.java).also { intent ->
                intent.putExtra(DetailNewsActivity.EXTRA_DATA, it)
                startActivity(intent)
            }
        }
    }

    private fun setViewModel() {
        newsViewModel.getNews.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> progressBar(true)
                is Resource.Success -> {
                    progressBar(false)
                    it.data?.let { data -> newsAdapter.setNews(data) }
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