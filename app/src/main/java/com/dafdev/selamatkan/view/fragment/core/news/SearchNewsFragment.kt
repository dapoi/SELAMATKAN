package com.dafdev.selamatkan.view.fragment.core.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.FragmentSearchNewsBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.hideKeyboard
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.news.SearchNewsAdapter
import com.dafdev.selamatkan.viewmodel.SearchNewsViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var searchNewsAdapter: SearchNewsAdapter

    private val searchNewsViewModel: SearchNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }

            svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.isNotEmpty()) {
                            binding.clEmptySearch.visibility = View.GONE
                            setViewModel(it)
                        } else {
                            binding.apply {
                                rvSearchNews.visibility = View.GONE
                                clEmptySearch.visibility = View.VISIBLE
                            }
                        }
                    }
                    hideKeyboard(requireActivity())
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        setAdapter()
    }

    private fun setViewModel(query: String) {
        searchNewsViewModel.getNewsSearch(query).observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        searchNewsAdapter.setNews(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        searchNewsAdapter = SearchNewsAdapter()
        binding.rvSearchNews.apply {
            adapter = searchNewsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        searchNewsAdapter.onItemClick = {
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
}