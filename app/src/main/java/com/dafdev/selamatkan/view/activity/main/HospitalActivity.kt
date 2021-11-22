package com.dafdev.selamatkan.view.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dafdev.selamatkan.R
import com.dafdev.selamatkan.databinding.ActivityHospitalBinding
import com.dafdev.selamatkan.view.adapter.pager.HospitalPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            val pagerAdapter = HospitalPagerAdapter(this@HospitalActivity)
            viewPager.adapter = pagerAdapter

            TabLayoutMediator(tabsHospital, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(R.string.covid, R.string.non_covid)
    }
}