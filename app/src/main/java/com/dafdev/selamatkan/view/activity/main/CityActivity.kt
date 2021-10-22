package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.data.source.RemoteDataSource
import com.dafdev.selamatkan.data.source.network.ApiConfig
import com.dafdev.selamatkan.databinding.ActivityCityBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.CityAdapter
import com.dafdev.selamatkan.viewmodel.CitiesViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Status

class CityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cityViewModel: CitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }

        setUpAdapter()
        setUpViewModel()
    }

    private fun setUpAdapter() {
        cityAdapter = CityAdapter(this)
        binding.rvCity.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CityActivity)
            adapter = cityAdapter
        }
    }

    private fun setUpViewModel() {
        val factory = ViewModelFactory(RemoteDataSource(ApiConfig.provideApiService()))
        cityViewModel = ViewModelProvider(this, factory)[CitiesViewModel::class.java]
        cityViewModel.dataCity(Constant.provinsiId).observe(this, {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> progressBar(true)
                    Status.SUCCESS -> {
                        progressBar(false)
                        cityAdapter.setCityAdapter(resource.data!!)
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