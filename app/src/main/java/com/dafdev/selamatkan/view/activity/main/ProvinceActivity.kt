package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.ActivityProvinceBinding
import com.dafdev.selamatkan.view.adapter.ProvinceInsideAdapter
import com.dafdev.selamatkan.viewmodel.ProvinceInsideViewModel
import com.dafdev.selamatkan.viewmodel.ViewModelFactory
import com.dafdev.selamatkan.vo.Resource
import com.google.android.material.snackbar.Snackbar

class ProvinceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProvinceBinding
    private lateinit var provinceAdapter: ProvinceInsideAdapter
    private lateinit var provinceVIewModel: ProvinceInsideViewModel

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
        provinceAdapter = ProvinceInsideAdapter(this)
        binding.rvProvince.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = provinceAdapter
        }
    }

    private fun setUpViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        provinceVIewModel = ViewModelProvider(this, factory)[ProvinceInsideViewModel::class.java]
        provinceVIewModel.getListProvInside().observe(this, {
            when (it) {
                is Resource.Loading -> progressBar(true)
                is Resource.Success -> {
                    progressBar(false)
                    provinceAdapter.setProvinceAdapter(it.data!!)
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