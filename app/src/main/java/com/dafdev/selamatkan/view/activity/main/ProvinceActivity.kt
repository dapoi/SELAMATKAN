package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.data.source.remote.RemoteDataSource
import com.dafdev.selamatkan.data.source.remote.network.ApiConfig
import com.dafdev.selamatkan.data.source.remote.response.ProvincesItem
import com.dafdev.selamatkan.databinding.ActivityProvinceBinding
import com.dafdev.selamatkan.view.adapter.ProvinceAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status

class ProvinceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProvinceBinding
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var provinceVIewModel: ProvinceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }

        setUpAdapter()
        setUpViewModel()
    }

    private fun setUpAdapter() {
        provinceAdapter = ProvinceAdapter(this)
        binding.rvProvince.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = provinceAdapter
        }
    }

    private fun setUpViewModel() {
        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiHospital()))
        provinceVIewModel = ViewModelProvider(this, factory)[ProvinceViewModel::class.java]
        provinceVIewModel.getListProv().observe(this, {
            it.let { resources ->
                when (resources.status) {
                    Status.LOADING -> progressBar(true)
                    Status.SUCCESS -> {
                        progressBar(false)
                        provinceAdapter.setProvinceAdapter(resources.data!! as List<ProvincesItem>)
                    }
                    Status.ERROR -> {
                        progressBar(false)
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
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