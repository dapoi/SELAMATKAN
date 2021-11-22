package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.ActivityProvinceCovidBinding
import com.dafdev.selamatkan.view.adapter.ProvinceCovidAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceCovidViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvinceCovidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProvinceCovidBinding
    private lateinit var covidAdapter: ProvinceCovidAdapter
    private val covidViewModel: ProvinceCovidViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinceCovidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        covidAdapter = ProvinceCovidAdapter()
        binding.rvCovidProv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = covidAdapter
        }
    }

    private fun setViewModel() {
        covidViewModel.dataCovidProv().observe(this, {
            when (it) {
                is Resource.Loading -> progressBar(true)
                is Resource.Success -> {
                    progressBar(false)
                    covidAdapter.setData(it.data!!)
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