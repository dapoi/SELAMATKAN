package com.dafdev.selamatkan.view.activity.core

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dafdev.selamatkan.databinding.ActivityDataCovidBinding
import com.dafdev.selamatkan.utils.HelpUtil
import com.dafdev.selamatkan.utils.HelpUtil.isOnline
import com.dafdev.selamatkan.utils.HelpUtil.showProgressBar
import com.dafdev.selamatkan.view.adapter.ProvinceCovidAdapter
import com.dafdev.selamatkan.viewmodel.DataCovidViewModel
import com.dafdev.selamatkan.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataCovidActivity : AppCompatActivity() {

    private var _binding: ActivityDataCovidBinding? = null
    private val binding get() = _binding!!
    private val dataCovidViewModel: DataCovidViewModel by viewModels()

    private lateinit var provinceCovidAdapter: ProvinceCovidAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDataCovidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            srlNews.setOnRefreshListener {
                val check = isOnline(this@DataCovidActivity)
                if (check) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val status = InternetReceiver()
                        status.onReceive(this@DataCovidActivity, intent)
                        srlNews.isRefreshing = false
                    }, 2000)
                } else {
                    rvProvinceCovid.visibility = View.GONE
                    viewNoConnected.visibility = View.VISIBLE
                    srlNews.isRefreshing = false
                }
            }

            toolbar.setNavigationOnClickListener { onBackPressed() }

            etSearch.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        val imm =
                            this@DataCovidActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)
                                    as InputMethodManager
                        imm.hideSoftInputFromWindow(rootView.windowToken, 0)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        provinceCovidAdapter.filter.filter(newText)
                        return false
                    }
                })
            }
        }

        provinceCovidAdapter = ProvinceCovidAdapter()

        binding.rvProvinceCovid.apply {
            setHasFixedSize(true)
            adapter = provinceCovidAdapter
            layoutManager = LinearLayoutManager(this@DataCovidActivity)
        }

        setViewModel()
    }

    private fun setViewModel() {
        dataCovidViewModel.dataCovidProv().observe(this) {
            binding.apply {
                when (it) {
                    is Resource.Loading -> progressBarProv.showProgressBar(true)
                    is Resource.Success -> {
                        progressBarProv.showProgressBar(false)
                        provinceCovidAdapter.setData(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBarProv.showProgressBar(false)
                        viewNoConnected.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    inner class InternetReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val status = isOnline(context)
            if (status) {
                HelpUtil.recreateActivity(this@DataCovidActivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}