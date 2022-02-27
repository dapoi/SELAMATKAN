package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.ActivityCityBinding
import com.dafdev.selamatkan.utils.Constant
import com.dafdev.selamatkan.view.adapter.CityAdapter
import com.dafdev.selamatkan.viewmodel.CitiesViewModel
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityBinding
    private lateinit var cityAdapter: CityAdapter
    private val cityViewModel: CitiesViewModel by viewModels()

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
        cityViewModel.dataCity(Constant.provinceId).observe(this) {
            when (it) {
                is Resource.Loading -> progressBar(true)
                is Resource.Success -> {
                    progressBar(false)
                    cityAdapter.setCityAdapter(it.data!!)
                }
                is Resource.Error -> {
                    progressBar(false)
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun progressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}