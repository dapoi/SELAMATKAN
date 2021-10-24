package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.data.source.RemoteDataSource
import com.dafdev.selamatkan.data.source.network.ApiConfig
import com.dafdev.selamatkan.databinding.ActivityProvinceCovidBinding
import com.dafdev.selamatkan.view.adapter.ProvinceCovidAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceCovidViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status
import com.google.android.material.snackbar.Snackbar

class ProvinceCovidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProvinceCovidBinding
    private lateinit var covidAdapter: ProvinceCovidAdapter
    private lateinit var covidViewModel: ProvinceCovidViewModel

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
        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiCovid()))
        covidViewModel = ViewModelProvider(this, factory)[ProvinceCovidViewModel::class.java]
        covidViewModel.dataCovidProv().observe(this, {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> progressBar(true)
                    Status.SUCCESS -> {
                        progressBar(false)
                        covidAdapter.setData(resource.data!!)
                    }
                    Status.ERROR -> {
                        progressBar(false)
                        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
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