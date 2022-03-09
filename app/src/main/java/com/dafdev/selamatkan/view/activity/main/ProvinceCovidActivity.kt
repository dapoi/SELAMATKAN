package com.dafdev.selamatkan.view.activity.main

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.ActivityProvinceCovidBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinceCovidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        HelpUtil.setStatusBarWhite(this, R.color.white)

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
        covidViewModel.dataCovidProv().observe(this) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        covidAdapter.setData(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}